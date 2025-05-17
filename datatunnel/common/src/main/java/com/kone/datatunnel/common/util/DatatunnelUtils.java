package com.kone.datatunnel.common.util;

import javax.sql.DataSource;

import com.gitee.melin.bee.core.extension.ExtensionLoader;
import com.gitee.melin.bee.core.jdbc.enums.DataSourceType;
import com.kone.datatunnel.api.DataTunnelSink;
import com.kone.datatunnel.api.DataTunnelSource;
import com.kone.datatunnel.api.DistCpAction;

/*
 * 描述：利用SPI机制，通过sourceType获取对应的DataTunnelSource实例
 */

public class DatatunnelUtils {
    // 获取DataTunnelSource实例
    public static DataTunnelSource getSourceConnector(DataSourceType sourceType){
        ExtensionLoader<DataTunnelSource> readLoader = ExtensionLoader.getExtensionLoader(DataTunnelSource.class);

        return readLoader.getExtension(sourceType.name().toLowerCase());
    }
    // 获取DataTunnelSink实例
    public static DataTunnelSink getSinkConnector(DataSourceType sinkType){
        ExtensionLoader<DataTunnelSink> readLoader = ExtensionLoader.getExtensionLoader(DataTunnelSink.class);

        return readLoader.getExtension(sinkType.name().toLowerCase());
    }
    // 获取DistCpAction实例
    public static DistCpAction getDistCpAction(){
        ExtensionLoader<DistCpAction> readLoader = ExtensionLoader.getExtensionLoader(DistCpAction.class);
        return readLoader.getExtension("distcp");
    }

}
