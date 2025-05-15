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

import com.kone.datatunnel.api.DataSourceType;
import com.kone.datatunnel.api.ParamKey;
import com.kone.datatunnel.api.model.DataTunnelOption;
import com.kone.datatunnel.common.annotation.SparkConfKey;



public class CommonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

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

            
        }
        return null;
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
        return "";
    }

    /*
     * 描述：去掉字符串中的引号
     */
    public static String cleanQuote(String value){
        return null;
    }

    /*
     * 描述：获取当前数据库
     */
    public static String getCurrentDatabase(String schemaName){
        return null;
    }

}
