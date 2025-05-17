package com.kone.datatunnel.common.util;

import java.sql.*;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kone.datatunnel.api.DataSourceType;


public class JdbcUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUtils.class);

    /*
     * 描述：创建JdbcUrl连接
     */
    public static String buildJdbcUrl(
        DataSourceType dsType, String host, int post, String databaseName, String schemaName
    ){
        String url = "";
        switch (dsType) {
            case MYSQL:
                url = String.format("jdbc:mysql://%s:%d?autoReconnect=true&characterEncoding=UTF-8&useServerPrepStmts=false&rewriteBatchedStatements=true&allowLoadLocalInfile=true", 
                    host, post
                );
                break;
            case DB2:
                url = String.format("jdbc:db2://%s:%d%s", 
                    host, post, 
                    StringUtils.isNotBlank(databaseName) ? String.format("/%s", databaseName):""
                );
                break;
            case POSTGRESQL:
            case GAUSSDWS:
            case GREENPLUM:
            case HASHDATA:
                url = String.format("jdbc:postgresql://%s:%d%s?reWriteBatchedInserts=true%s", 
                    host, post, 
                    StringUtils.isNotBlank(databaseName)?String.format("/%s", databaseName):"", 
                    StringUtils.isNotBlank(schemaName)?String.format("&currentSchema=%s", schemaName):""
                );
                break;
            case SQLSERVER:
                url = String.format("jdbc:sqlserver://%s:%d;database=%s;trustServerCertificate=true", 
                    host, post, databaseName
                );
                break;
            case HANA:
                url = String.format("jdbc:dm://%s:%d/%s", 
                    host, post, databaseName
                );
                break;
            case OCEANBASE:
                url = String.format("jdbc:oceanbase://%s:%d/%s?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true&allowMultiQueries=true", 
                    host, post, databaseName
                );
                break;
        }
        return url;
    }

    // 执行SQL语句
    public static void execute(Connection conn, String sql, List<Object> parameters){
        PreparedStatement stmt = null;
        try{

        } finally {

        }
    }

    // 关闭 Jdbc connection
    public static void close(Connection conn){
        if(conn == null) return;
        try{
            if(conn.isClosed()) return;
            conn.close();
        }catch (SQLRecoverableException e){
            // 跳过
        }catch (Exception e){
            LOG.debug("关闭Jdbc connection失败", e);
        }
    }
}
