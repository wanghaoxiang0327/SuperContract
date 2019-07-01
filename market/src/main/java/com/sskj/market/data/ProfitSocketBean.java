package com.sskj.market.data;

import java.util.List;

public class ProfitSocketBean {


    /**
     * code : BTC/USDT
     * name : BTC/USDT
     * timestamp : 1561956675
     * recs : [{"selltime":1561956675,"type":1,"income":"0.0300","ptype":"ETH","sellprice":"11150.61000000"},{"selltime":1561956664,"type":1,"income":"0.2250","ptype":"ETH","sellprice":"11149.92000000"},{"selltime":1561956654,"type":1,"income":"0.0300","ptype":"ETH","sellprice":"11146.38000000"},{"selltime":1561956644,"type":1,"income":"0.1500","ptype":"ETH","sellprice":"11135.59000000"},{"selltime":1561956493,"type":1,"income":"0.0375","ptype":"ETH","sellprice":"11189.94000000"},{"selltime":1561956493,"type":1,"income":"2.2500","ptype":"ETH","sellprice":"11189.94000000"},{"selltime":1561956371,"type":1,"income":"2.2500","ptype":"ETH","sellprice":"11216.54000000"},{"selltime":1561956351,"type":1,"income":"0.3000","ptype":"ETH","sellprice":"11198.64000000"},{"selltime":1561956341,"type":1,"income":"0.0150","ptype":"ETH","sellprice":"11197.35000000"},{"selltime":1561956331,"type":1,"income":"0.0300","ptype":"ETH","sellprice":"11197.20000000"},{"selltime":1561956331,"type":1,"income":"0.0150","ptype":"ETH","sellprice":"11197.20000000"},{"selltime":1561956311,"type":1,"income":"0.0375","ptype":"ETH","sellprice":"11184.01000000"},{"selltime":1561956240,"type":1,"income":"2.2500","ptype":"ETH","sellprice":"11197.69000000"},{"selltime":1561956240,"type":1,"income":"0.0750","ptype":"ETH","sellprice":"11197.69000000"},{"selltime":1561956220,"type":1,"income":"0.0300","ptype":"ETH","sellprice":"11188.52000000"},{"selltime":1561956159,"type":1,"income":"0.0075","ptype":"ETH","sellprice":"11196.02000000"},{"selltime":1561956149,"type":1,"income":"0.3000","ptype":"ETH","sellprice":"11194.31000000"},{"selltime":1561956149,"type":1,"income":"0.0150","ptype":"ETH","sellprice":"11194.31000000"},{"selltime":1561956149,"type":1,"income":"0.3000","ptype":"ETH","sellprice":"11194.31000000"},{"selltime":1561956149,"type":1,"income":"0.0225","ptype":"ETH","sellprice":"11194.31000000"}]
     */

    private String code;
    private String name;
    private int timestamp;
    private List<OrderBean> recs;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<OrderBean> getRecs() {
        return recs;
    }

    public void setRecs(List<OrderBean> recs) {
        this.recs = recs;
    }


}
