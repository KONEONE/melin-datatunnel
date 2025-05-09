package com.kone.datatunnel.api;

import org.apache.spark.sql.SparkSession;

import com.kone.datatunnel.api.model.DataTunnelSinkOption;
import com.kone.datatunnel.api.model.DataTunnelSourceOption;

import lombok.Data;
/*
 * 描述：source + sink + transfrom 的上下文
 */
@Data
public class DataTunnelContext {
    /// Source 类型 + 配置信息
    private DataSourceType sourceType;
    private DataTunnelSourceOption sourceOption;
    /// Sink 类型 + 配置信息 
    private DataSourceType sinkType;
    private DataTunnelSinkOption sinkOption;
    /// 转化 SQL
    private String transfromSql;
    /// sparksession
    private SparkSession sparkSession = SparkSession.active();
}
