package io.github.melin.jobserver.spark.api;

import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import io.github.melin.jobserver.JobServerLogEvent;

import org.apache.spark.scheduler.LiveListenerBus;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;

public class LogUtils {
    private static final Logger Log = LoggerFactory.getLogger(LogUtils.class);

    /*
     * 1. 格式化日志消息。
     * 2. 获取 Spark 的 LiveListenerBus。
     * 3. 创建 JobServerLogEvent 对象，指定日志级别为 INFO。
     * 4. 将日志事件发布到 LiveListenerBus。
     * 5. 调用 SLF4J 的 info 方法记录日志。
     */
    private static void logout(LogLevel logLevel, String format, Object... params){
        String msg = format;
        if(params.length > 0){
            msg = MessageFormatter.arrayFormat(format, params).getMessage();
        }

        LiveListenerBus listenerBus = SparkSession.active().sparkContext().listenerBus();
        JobServerLogEvent logEvent = new JobServerLogEvent(logLevel, msg);
        Log.info(format, params);
    }

    public static void info(String format, Object... params){
        logout(LogLevel.INFO, format, params);
    }

    public static void warn(String format, Object... params){
        logout(LogLevel.WARN, format, params);
    }

    public static void error(String format, Object... params){
        logout(LogLevel.ERROR, format, params);
    }
    
    public static void stdout(String format, Object... params){
        String msg = format;
        if(params.length > 0){
            msg = MessageFormatter.arrayFormat(format, params).getMessage();
        }

        LiveListenerBus listenerBus = SparkSession.active().sparkContext().listenerBus();
        JobServerLogEvent logEvent = new JobServerLogEvent(LogLevel.STDOUT, msg);
        listenerBus.post(logEvent);
    }

}
