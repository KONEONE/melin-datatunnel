package com.kone.datatunnel.common.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.sql.SparkSession;

/*
 * 描述： hadoop的FileSystem工具类
 */
public class FsUtils {
    public static void mkDir(SparkSession sparkSession, String path) throws IOException{
        Configuration hadoopConfiguration = sparkSession.sparkContext().hadoopConfiguration();
        FileSystem fs = FileSystem.get(hadoopConfiguration);
        if(!fs.exists(new Path(path))){
            fs.mkdirs(new Path(path));
        }
    }
}
