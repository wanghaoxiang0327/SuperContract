package com.sskj.common.http;

public class ApiException extends Throwable {
    private String msg;

    public ApiException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
