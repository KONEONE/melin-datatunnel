package com.kone.datatunnel.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.gitee.melin.bee.core.extension.SPI;
import com.kone.datatunnel.api.model.DataTunnelSinkOption;

@SPI
public interface  DataTunnelSink extends Serializable {
    // 定义一个将数据集下沉到数据隧道的方法
    void sink(Dataset<Row> dataset, DataTunnelContext context) throws IOException;

    // 获取数据隧道下沉选项的类
    Class<? extends DataTunnelSinkOption> getOptionClass();

    // 根据数据集和数据隧道上下文创建表
    default void createTable(Dataset<Row> dataset, DataTunnelContext context) {}

    // 获取可选选项的集合
    default Set<String> optionalOptions(){
        return Collections.emptySet();
    }
}