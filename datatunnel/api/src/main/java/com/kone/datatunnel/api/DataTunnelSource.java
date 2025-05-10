package com.kone.datatunnel.api;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.apache.arrow.vector.table.Row;
import org.apache.spark.sql.Dataset;

import com.gitee.melin.bee.core.extension.SPI;
import com.kone.datatunnel.api.model.DataTunnelSourceOption;

@SPI
public interface DataTunnelSource extends Serializable{
    // 读取数据源中的数据并转换为Spark Dataset
    Dataset<Row> read(DataTunnelContext context) throws Exception;

    // 获取数据源配置类
    Class<? extends DataTunnelSourceOption> getOptionClass();

    // 是否支持CTE
    default boolean supportCte() {
        return false;
    }

    // 可选配置项
    default Set<String> optionalOptions() {
        return Collections.emptySet();
    }
}
