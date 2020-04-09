package com.sskj.market.data;

import java.util.List;

public class TradeCoin {


    /**
     * pid : 4
     * pname : EOS
     * mark : EOS/USDT
     * price : 2.14810000
     * aim_point : ["30","60","120"]
     * odds : ["0.75","0.75","0.75"]
     */

    private String pid;
    private String pname;
    private String mark;
    private String price;
    //最小变动价
    private double min_price;
    ////目标点位
    private List<String> aim_point;
    //赔率
    private List<String> odds;


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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAim_point() {
        return aim_point;
    }

    public void setAim_point(List<String> aim_point) {
        this.aim_point = aim_point;
    }

    public List<String> getOdds() {
        return odds;
    }

    public void setOdds(List<String> odds) {
        this.odds = odds;
    }

    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

}
