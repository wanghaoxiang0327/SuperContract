package com.sskj.common.rxbus;

/**
 * Created by lvzhihao on 17-7-25.
 */

public class BusData {
    String id;
    String status;

    public BusData() {
    }

    public BusData(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
