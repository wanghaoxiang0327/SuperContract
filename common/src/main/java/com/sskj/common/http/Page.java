package com.sskj.common.http;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Page<T> {

    @JSONField(name = "res",alternateNames = {"res","list"})
    private List<T> res;
    private int p;
    private int count;
    private int size;

    public List<T> getRes() {
        return res;
    }

    public void setRes(List<T> res) {
        this.res = res;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
