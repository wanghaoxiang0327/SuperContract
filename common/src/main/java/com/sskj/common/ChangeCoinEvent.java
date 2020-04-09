package com.sskj.common;

public class ChangeCoinEvent {

    private String code;
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ChangeCoinEvent(String code, String pid) {
        this.code = code;
        this.pid = pid;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
