package com.kone.datatunnel.api;

public class DataTunnelException extends RuntimeException{
    public DataTunnelException(String msg){
        super(msg);
    }

    public DataTunnelException(String msg, Throwable cause){
        super(msg, cause);
    }
}