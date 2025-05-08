package com.kone.datatunnel.api.model;

public interface DataTunnelSinkOption extends DataTunnelOption {
    // 获取Sink列名
    String[] getColumns();
}
