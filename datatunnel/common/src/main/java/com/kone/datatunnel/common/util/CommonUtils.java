package com.kone.datatunnel.common.util;

import java.lang.reflect.Field;
import java.util.Map;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gitee.melin.bee.util.JsonUtils;
import com.kone.datatunnel.api.DataSourceType;
import com.kone.datatunnel.api.DataTunnelException;
import com.kone.datatunnel.api.ParamKey;
import com.kone.datatunnel.api.model.DataTunnelOption;
import com.kone.datatunnel.common.annotation.SparkConfKey;



public class CommonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);
    private static final String[] SEspecialList = new String[]{"'","\"", "`"};

    /*
     * 描述：将配置信息转换为SparkConf
     */
    public static void convertOptionToSparkConf(SparkSession sparkSession, Object obj){
        try{
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field filed: fields){
                SparkConfKey confKey = filed.getAnnotation(SparkConfKey.class);
                if(confKey == null){
                    continue;
                }

                String sparkKey = confKey.value();
                filed.setAccessible(true);
                Object value = filed.get(obj);

                if(value == null){
                    sparkSession.conf().unset(sparkKey);
                } else {
                    sparkSession.conf().set(sparkKey, String.valueOf(value));
                    LOG.info("添加SparkConf配置: {}={}", sparkKey, String.valueOf(value));
                }
            }
        }catch(Exception e){
            LOG.error("配置转SparkConf:"+e.getMessage(), e);
            
        }
    }

    // 定义一个静态常量VALODATOR，类型为Validator
    public static final Validator VALODATOR = Validation.byDefaultProvider()
            // 使用默认的验证提供者
            .configure()
            // 配置验证器
            .messageInterpolator(new ParameterMessageInterpolator())
            // 设置消息插值器为ParameterMessageInterpolator
            .buildValidatorFactory()
            // 构建验证器工厂
            .getValidator();
    
    /*
     * 描述： 将Map<String, String> 转化 clazz 类型的对象，其中
     * 包含了字段名称的修改等操作
     */
    public static <T> T toJavaBean(Map<String, String> map, Class<T> clazz, String msg) throws Exception {
        T beanInstance = clazz.getConstructor().newInstance();

        Map<String, String> properties = null;
        if (beanInstance instanceof DataTunnelOption){
            properties = ((DataTunnelOption) beanInstance).getProperties();
        }

        Map<String, String> keyAliasMap = Maps.newHashMap();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            ParamKey paramKey = field.getAnnotation(ParamKey.class);
            if(paramKey == null){
                continue;
            }
            keyAliasMap.put(paramKey.value(), field.getName());
        }

        for(String filedName: map.keySet()){
            String value = map.get(filedName);
            if(properties != null && StringUtils.startsWith(filedName, "properties.")){
                String key = StringUtils.substringAfter(filedName, "properties.");
                properties.put(key, value);
                continue;
            }

            // 更换字段名
            if(keyAliasMap.containsKey(filedName)){
                filedName = keyAliasMap.get(filedName);
            }

            Field field = ReflectionUtils.findField(clazz, filedName);
            if(field == null){
                throw new DataTunnelException(msg + " Field: "+filedName);
            }

            field.setAccessible(true);
            // 匹配数据类型
            if (field.getType() == String.class) {
                field.set(beanInstance, value);
            } else if (field.getType() == Integer.class || field.getType() == int.class) {
                field.set(beanInstance, Integer.parseInt(value));
            } else if (field.getType() == Long.class || field.getType() == long.class) {
                field.set(beanInstance, Long.parseLong(value));
            } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                field.set(beanInstance, Boolean.valueOf(value));
            } else if (field.getType() == Float.class || field.getType() == float.class) {
                field.set(beanInstance, Float.parseFloat(value));
            } else if (field.getType() == Double.class || field.getType() == double.class) {
                field.set(beanInstance, Double.parseDouble(value));
            } else if (field.getType() == String[].class) {
                field.set(beanInstance, JsonUtils.toJavaObject(value, new TypeReference<String[]>() {}));
            } else if (field.getType().isEnum()) {
                field.set(beanInstance, Enum.valueOf((Class<Enum>) field.getType(), value.toUpperCase()));
            } else {
                throw new DataTunnelException(filedName + " not support data type: " + field.getType());
            }
            field.setAccessible(false);
        }
        return beanInstance;
    }

    /*
     * 描述：生成输出SQL
     */
    @NotNull
    public static String genOutputSql(
        Dataset<Row> dataset,
        String[] sourceColumns,
        String[] sinkColumns,
        DataSourceType dataSourceType
    ) throws AnalysisException {
        String tdlName = String.format("tdl_datatunnel_%s_%d", dataSourceType.name().toLowerCase(), System.currentTimeMillis());
        dataset.createTempView(tdlName);

        String sql;

        if(sourceColumns.length != sinkColumns.length){
            // 处理 insert into sinkTable (c1, c2) values select * from sourceTable 的情况
            if((sourceColumns.length ==1 && "*".equals(sourceColumns[0]))&&(sinkColumns.length > 1)){
                sql = "select "+ StringUtils.join(sinkColumns, ",") + " from " + tdlName;
            }
            // 处理 insert into sinkTable values select x1, x2 from sourceTable 的情况
            else if((sinkColumns.length == 1 && "*".equals(sinkColumns[0]))&&(sourceColumns.length > 1)){
                sql = "select * from " + tdlName;
            }
            // 处理 insert into sinkTable (c1, c2) values select x1, x2, x3 from sourceTable 的情况
            else{
                String msg = String.format(
                    "不支持该列映射关系：source cols: %s -> sink cols: %s", 
                    StringUtils.join(sourceColumns, ","), 
                    StringUtils.join(sinkColumns, ","));
                throw new UnsupportedOperationException(msg);
            }
        }else {
            // 处理 insert into sinkTable values select * from sourceTable 的情况
            if((sourceColumns.length==1 && "*".equals(sourceColumns[0]))&&(sinkColumns.length==1 && "*".equals(sinkColumns[0]))){
                sql = "select * from " + tdlName;
            }
            // 处理 insert into sinkTable (c1, c2) values select x1, x2 from sourceTable 的情况
            else{
                String[] tmpCols = new String[sinkColumns.length];
                for(int i=0;i<sinkColumns.length; ++i){
                    if(sourceColumns[i].equals(sinkColumns[i])){
                        tmpCols[i] = sourceColumns[i];
                    }else{
                        tmpCols[i] = String.format("%s as %s", sourceColumns[i], sinkColumns[i]);
                    }
                }
                sql = String.format("select %s from %s", StringUtils.join(tmpCols, ","), tdlName);
            }
        }

        return sql;
    }

    /*
     * 描述：去掉首位无关字符
     */
    public static String cleanQuote(String value){
        if(value==null) return null;
        String res = value;
        for(String s : SEspecialList){
            if(StringUtils.startsWith(value, s) && StringUtils.endsWith(value, s)){
                res = StringUtils.substring(value, 1, -1);
                return res.trim();
            }
        }
        return res.trim();
    }

    /*
     * 描述：获取当前Spark会话中的数据库
     */
    public static String getCurrentDatabase(String schemaName){
        if(schemaName != null){
            return schemaName;
        }
        return SparkSession.getActiveSession().get().catalog().currentDatabase();
    }

}
