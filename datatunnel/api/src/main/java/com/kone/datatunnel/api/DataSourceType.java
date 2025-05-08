package com.kone.datatunnel.api;

public enum DataSourceType {
    MYSQL,
    TISPARK,
    ORACLE,
    SQLSERVER,
    POSTGRESQL,
    TERADATA,
    GAUSSDWS,
    GREENPLUM,
    HASHDATA,
    DB2,
    HANA,
    DAMENG,
    OCEANBASE,
    MAXCOMPUTE,
    REDSHIFT,
    SNOWFLAKE,
    DORIS,
    STARROCKS,

    HIVE,
    HUDI,
    PAIMON,
    DELTA,
    ICEBERG,

    SPARK, // HIVE 别名
    KAFKA,
    CLICKHOUSE,
    CASSANDRA,
    HBASE,
    MONGODB,
    LOG,
    EXCEL,
    SFTP,
    FTP,
    S3,
    OSS,
    COS,
    MINIO,
    HDFS,
    REDIS,
    ELASTICSEARCH;

    /*
     * 描述： 是否为Jdbc源
     */
    public static boolean isJdbcDataSource(DataSourceType dsType){
        switch(dsType){
            case MYSQL:
            case ORACLE:
            case POSTGRESQL:
            case DB2:
            case GAUSSDWS:
            case GREENPLUM:
            case HANA:
            case DAMENG:
                return true;
        }
        return false;
    }
}
