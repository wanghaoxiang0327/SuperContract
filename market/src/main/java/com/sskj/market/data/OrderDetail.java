package com.sskj.market.data;

public class OrderDetail {

    /**
     * id : 2
     * pname : ETH/USDT
     * type : 1
     * ptype : USDT
     * income : 7.5000
     * buyprice : 270.00000000
     * sellprice : 289.00000000
     * state : 2
     * pc_type : 1
     * url : http:47.75.88.37/wap/register.html?tjuser=AKG52034171
     * qrc : http:47.75.88.37/Poster/52034171.png
     * tgno : AKG52034171
     */

    private String id;
    private String pname;
    private int type;
    private String ptype;
    private String income;
    private String buyprice;
    private String sellprice;
    private String state;
    private String pc_type;
    private String url;
    private String qrc;
    private String tgno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice;
    }

    public String getSellprice() {
        return sellprice;
    }

    public void setSellprice(String sellprice) {
        this.sellprice = sellprice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPc_type() {
        return pc_type;
    }

    public void setPc_type(String pc_type) {
        this.pc_type = pc_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQrc() {
        return qrc;
    }

    public void setQrc(String qrc) {
        this.qrc = qrc;
    }

    public String getTgno() {
        return tgno;
    }

    public void setTgno(String tgno) {
        this.tgno = tgno;
    }
}
