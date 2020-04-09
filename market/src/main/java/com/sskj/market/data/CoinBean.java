package com.sskj.market.data;

import java.io.Serializable;

public class CoinBean implements Serializable {

    /**
     * code : btc_usdt
     * name : BTC
     * date : 2018-08-23
     * time : 17:54:52
     * price : 6403.4300
     * open : 6673.9800
     * close : 6403.4300
     * high : 6686.8800
     * low : 6250.0000
     * volume : 18464.7247
     * buy : 6402.9400
     * sell : 6403.4300
     * change : -270.5500
     * changeRate : -4.05%
     * cnyPrice :
     */

    private String code;
    private String name;
    private String date;
    private String time;
    private double price;
    private double open;
    private double close;
    private double high;
    private double low;
    private double volume;
    private String buy;
    private String sell;
    private double change;
    private String changeRate;
    private String cnyPrice;
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isUp() {
        try {
            if (change > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }


    public CoinBean() {

    }

    public void setChange(double change) {
        this.change = change;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public String getChangeRate() {
        return changeRate == null ? "" : changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getCnyPrice() {
        return cnyPrice == null ? "" : cnyPrice;
    }

    public void setCnyPrice(String cnyPrice) {
        this.cnyPrice = cnyPrice;
    }


}
