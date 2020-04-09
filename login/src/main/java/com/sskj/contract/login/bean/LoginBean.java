package com.sskj.contract.login.bean;

public class LoginBean {

    /**
     * token : cf7ec8187ab3da4c52c7ca02283edd9c
     * account : 3841121
     * is_start_google : 1
     * is_start_sms : 1
     * realname : 山人
     */

    private String token;
    private String account;
    private String is_start_google;
    private String is_start_sms;
    private String realname;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIs_start_google() {
        return is_start_google;
    }

    public void setIs_start_google(String is_start_google) {
        this.is_start_google = is_start_google;
    }

    public String getIs_start_sms() {
        return is_start_sms;
    }

    public void setIs_start_sms(String is_start_sms) {
        this.is_start_sms = is_start_sms;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
