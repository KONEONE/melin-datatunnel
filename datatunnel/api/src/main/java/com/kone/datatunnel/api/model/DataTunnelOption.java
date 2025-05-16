package com.kone.datatunnel.api.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.kone.datatunnel.api.DataSourceType;

public interface DataTunnelOption {
    /// 获取参数
    default Map<String, String> getParams(){
        try{
            // 创建一个HashMap用于存储参数
            HashMap<String,String> params = new HashMap<String, String>();
            // 获取当前类的所有字段
            Field[] fields = this.getClass().getDeclaredFields();
            // 遍历所有字段
            for(Field f : fields){
                // 设置字段可访问
                f.setAccessible(true);
                // 获取字段名
                String k = f.getName();
                // 获取字段值
                String v = String.valueOf(f.get(this));
                // 如果字段值和字段名都不为空，则将字段名和字段值存入HashMap
                if(v != null && k != null){
                    params.put(k, v);
                }
            }
            // 返回HashMap
            return params;
        }catch (Exception e){
            // 如果发生异常，则抛出异常
            throw new IllegalStateException("build params failed: "+e.getMessage());
        }
    }

    /// 获取数据源类型
    DataSourceType getDataSourceType();

    /// 获取CTE SQL语句
    default String getCteSql(){
        return null;
    }

    /// 设置CTE SQL语句
    default void setCteSql(String cteSql) {}

    /// 获取属性
    Map<String, String> getProperties();
}