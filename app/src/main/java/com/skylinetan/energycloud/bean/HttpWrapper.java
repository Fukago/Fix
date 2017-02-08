package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/13.
 */
public class HttpWrapper<T> {

    private int status;
    private String msg;
    private T data;

    public T getData(){
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
