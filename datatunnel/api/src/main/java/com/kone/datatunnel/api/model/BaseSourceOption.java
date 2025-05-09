package com.kone.datatunnel.api.model;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.apache.curator.shaded.com.google.common.collect.Maps;

import com.kone.datatunnel.api.DataSourceType;

/*
 * 描述：实现DataTunnelSourceOption基础类，其核心功能如下：
 *  1. 定义数据源类型：通过dataSourceType属性指定数据源类型，支持多种数据库和数据存储系统
 *  2. 提供列选择功能：通过columns属性指定需要读取的列，支持多种表达式格式
 *  3. 管理临时视图：通过sourceTempView属性设置源数据的临时视图名称
 *  4. 支持CTE SQL：通过cteSql属性支持公共表表达式SQL
 *  5. 管理自定义属性：通过properties属性存储各种自定义配置项
 */

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
     *     5.1 " " : 空
     *     5.2 null : null
     *     5.3 “null” : null
     *  6. to_char(a+1) 为计算字符串长度函数
     *  7. 2.3 为浮点数
     *  8. true 为布尔值
     */
    @NotEmpty(message =  "Columns can not empty")
    private String[] columns = new String[]{"*"};

    /*
     * 描述：key 前缀properties的参数，全部写入 properties
     */
    private final Map<String, String> properties = Maps.newHashMap();

    /// DataSourceType 方法
    @Override
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType){
        this.dataSourceType = dataSourceType;
    }

    /// Properties 方法
    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    /// SourceTempView 方法
    @Override
    public String getSourceTempView() {
        return sourceTempView;
    }

    @Override
    public void setSourceTempView(String sourceTempView) {
        this.sourceTempView = sourceTempView;
    }

    /// Columns 方法
    @Override
    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns){
        this.columns = columns;
    }

    /// CteSql 方法
    public void setCteSql(String cteSql){
        this.cteSql = cteSql;
    }

    public String getCteSql(){
        return cteSql;
    }
}
