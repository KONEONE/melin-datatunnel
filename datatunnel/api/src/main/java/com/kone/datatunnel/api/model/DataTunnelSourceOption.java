package com.kone.datatunnel.api.model;

public interface DataTunnelSourceOption extends DataTunnelOption{
    // 获取源临时视图
    String getSourceTempView();
    // 设置源模板视图
    void setSourceTempView(String sourceTempView);
    // 获取source列
    String[] getColumns();
}