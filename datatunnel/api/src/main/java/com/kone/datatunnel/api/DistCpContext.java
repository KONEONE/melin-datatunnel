package com.kone.datatunnel.api;

import org.apache.spark.sql.SparkSession;

import com.kone.datatunnel.api.model.DistCpOption;

import lombok.Data;

@Data
public class DistCpContext {
    private DistCpOption option;

    private SparkSession sparkSession = SparkSession.active();
}
