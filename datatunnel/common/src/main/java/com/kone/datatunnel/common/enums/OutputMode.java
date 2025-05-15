package com.kone.datatunnel.common.enums;

// 支持的插入方式
public enum OutputMode {
    APPEND("append"),
    COMPLETE("complete");

    private String name;

    OutputMode(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
