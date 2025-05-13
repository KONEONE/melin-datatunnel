package io.github.melin.jobserver;

import org.apache.spark.scheduler.SparkListenerEvent;

import io.github.melin.jobserver.spark.api.LogLevel;

public class JobServerLogEvent implements SparkListenerEvent{
    private final LogLevel logLevel;
    private final String msg;

    public JobServerLogEvent(LogLevel logLevel, String msg){
        this.logLevel = logLevel;
        this.msg = msg;
    }

    public LogLevel getLogLevel(){
        return this.logLevel;
    }

    public String getMsg(){
        return this.msg;
    }

    @Override
    public String toString(){
        return "JobServerLogEvent{"+"logLevel="+logLevel+", msg="+msg+" }";
    }
}
