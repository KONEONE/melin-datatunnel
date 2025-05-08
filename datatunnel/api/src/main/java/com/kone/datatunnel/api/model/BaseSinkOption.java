package com.kone.datatunnel.api.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.apache.curator.shaded.com.google.common.collect.Maps;

import com.kone.datatunnel.api.DataSourceType;

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
