package com.kone.datatunnel.api.model;

import java.util.Map;

import com.kone.datatunnel.api.DataSourceType;

public class BaseSourceOption implements DataTunnelSourceOption{
    private DataSourceType dataSourceType;
    private String sourceTempView;
    private String cteSql;

    /**
     *      支持表达式（包含：常量）：您需要按照Source database SQL语法格式
     * 例如：['id', 'table', '1', "'mingya.wmy'", "'null'", "to_char(a+1)", "2.3", "true"]
     *  1. id : 普通列名
     *  2. table : 列名
     *  3. 1 ： 数字常数
     *  4. mingya.wmy ： 字符串常量
     *  5. 关于null:
     *          " " ： 空
     *          null : null
     *          “null” : null
     */
    @Override
    public DataSourceType getDataSourceType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDataSourceType'");
    }

    @Override
    public Map<String, String> getProperties() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProperties'");
    }

    @Override
    public String getSourceTempView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSourceTempView'");
    }

    @Override
    public void setSourceTempView(String sourceTempView) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSourceTempView'");
    }

    @Override
    public String[] getColumns() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColumns'");
    }

}
