package com.sskj.market.data;

public class OrderBean {

    /**
     * selltime : 1561026999
     * type : 1
     * income : 7.5000
     * ptype : USDT
     * sellprice : 289.00000000
     */

    private long selltime;
    private int type;
    private String income;
    private String ptype;
    private String sellprice;

    public long getSelltime() {
        return selltime;
    }

    public void setSelltime(long selltime) {
        this.selltime = selltime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getSellprice() {
        return sellprice;
    }

    public void setSellprice(String sellprice) {
        this.sellprice = sellprice;
    }
}
