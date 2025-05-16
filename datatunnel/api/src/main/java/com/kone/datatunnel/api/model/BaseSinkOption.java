package com.kone.datatunnel.api.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.apache.curator.shaded.com.google.common.collect.Maps;

import com.kone.datatunnel.api.DataSourceType;

/*
 * 描述：为各种不同类型的数据写入连接器提供一个统一的基础实现
 *  1. 数据源类型：通过dataSourceType属性指定Sink类型
 *  2. 管理自定义属性：通过properties属性存储各种自定义配置项
 *  3. 提供列选择功能：管理要写入的数据列集合，默认为所有列（"*"）
 *  4. 作为扩展基类：各种SinkOption的基类，比如Jdbc
 */
public class BaseSinkOption implements DataTunnelSinkOption {
    private DataSourceType dataSourceType;
    
    private final Map<String, String> properties = Maps.newHashMap();

    @NotEmpty(message = "columns can not empty")
    private String[] columns = new String[] {"*"};

    @Override
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType){
        this.dataSourceType = dataSourceType;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns){
        this.columns = columns;
    }
}