package com.sskj.asset.data;

public class WithdrawRecordsBean {

    /**
     * id : 1
     * addtime : 2018-12-06 12:01:36
     * price : 200.0000
     * state : 4
     * pid : 0
     * qianbao_url : 1K2m9iGZuPXvgeiyW9nyiCH3Z5FNVZ9kRe
     * check_time : 2018-12-06 12:01:50
     * pname : null
     */

    private String id;
    private String addtime;
    private String price;
    private int state;
    private String pid;
    private String qianbao_url;
    private String check_time;
    private Object pname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQianbao_url() {
        return qianbao_url;
    }

    public void setQianbao_url(String qianbao_url) {
        this.qianbao_url = qianbao_url;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public Object getPname() {
        return pname;
    }

    public void setPname(Object pname) {
        this.pname = pname;
    }
}
