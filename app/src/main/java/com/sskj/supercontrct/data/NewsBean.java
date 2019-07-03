package com.sskj.supercontrct.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsBean implements Serializable {

    /**
     * id : 26
     * title : 虚拟实盘交易大赛场景（十）
     * date : 2019-04-09 12:14:00
     * is_read : 0
     */

    private String id;

    @JSONField(name = "title", alternateNames = {"date", "bm_title"})
    private String title;
    @JSONField(name = "date", alternateNames = {"date", "issue_time"})
    private String date;
    private String content;
    private int is_read;
    private String pic_addr;
    private String context;

    public String getPic_addr() {
        return pic_addr;
    }

    public void setPic_addr(String pic_addr) {
        this.pic_addr = pic_addr;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }
}
