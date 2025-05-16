package com.kone.datatunnel.api.model;

import java.util.Map;

import org.apache.curator.shaded.com.google.common.collect.Maps;

import com.kone.datatunnel.api.DataSourceType;

/*
 * 描述：
 *  1. 定义源数据类型
 *  2. 定义临时表名
 *  3. 管理自定义属性
 */
public class BaseCommonOption implements DataTunnelOption{
    private DataSourceType dataSourceType;
    private String sourceTempView;

    // key 前缀为 properties 的参数，全部写入 properties
    private final Map<String, String> properties = Maps.newHashMap();

    @Override
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType){
        this.dataSourceType = dataSourceType;
    }

    public String getSourceTempView(){
        return sourceTempView;
    }

    public void setSourceTempView(String sourceTempView){
        this.sourceTempView = sourceTempView;
    }
    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

}