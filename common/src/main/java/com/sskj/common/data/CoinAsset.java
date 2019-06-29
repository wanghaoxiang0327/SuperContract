package com.sskj.common.data;

import java.util.List;

public class CoinAsset {


    /**
     * pid : 2
     * pname : BTC
     * name : 比特币
     * aim_point : ["0.01","0.1","1"]
     */

    private String pid;
    private String pname;
    private String name;

    //单位交易量
    private List<String> aim_point;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAim_point() {
        return aim_point;
    }

    public void setAim_point(List<String> aim_point) {
        this.aim_point = aim_point;
    }
}
