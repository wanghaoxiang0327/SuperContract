package com.sskj.market.data;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.tifezh.kchartlib.chart.entity.IBOLL;
import com.github.tifezh.kchartlib.chart.entity.IKDJ;
import com.github.tifezh.kchartlib.chart.entity.IKLine;
import com.github.tifezh.kchartlib.chart.entity.IMACD;
import com.github.tifezh.kchartlib.chart.entity.IMinuteLine;
import com.github.tifezh.kchartlib.chart.entity.IRSI;
import com.github.tifezh.kchartlib.chart.entity.IWR;

import java.util.Date;
import java.util.NavigableMap;

/**
 * Created by Administrator on 2018/4/3.
 */

public class Stock implements IKLine, IMinuteLine, IBOLL, IKDJ, IMACD, IRSI, IWR {


    public float MA5Price;

    public float MA10Price;

    public float MA20Price;

    public float dea;

    public float dif;

    public float macd;

    public float k;

    public float d;

    public float j;

    public float rsi1;

    public float rsi2;

    public float rsi3;

    public float up;

    public float mb;

    public float dn;

    public float MA5Volume;

    public float MA10Volume;
    /**
     * id : 2271121
     * code : BTC/USDT
     * period :
     * volume : 1.73359092
     * price :
     * openPrice : 7611.414
     * closePrice : 7611.4262
     * prevClose :
     * high : 7615.667
     * low : 7610
     * date : 1533114360000
     * createTime :
     * isDeleted : 0
     * timestamp : 1533114400000
     */

    private String code;
    private double volume;
    private String price;
    @JSONField(name = "openingPrice")
    private double open;
    @JSONField(name = "closingPrice")
    private double close;
    @JSONField(name = "highestPrice")
    private double high;
    @JSONField(name = "lowestPrice")
    private double low;
    private long timestamp;
    public float wr;


    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setPrice(String price) {
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public float getVolume() {
        return (float) volume;
    }

    @Override
    public float getMA5Volume() {
        return MA5Volume;
    }

    @Override
    public void setMA5Volume(float v) {
        MA5Volume = v;
    }

    @Override
    public float getMA10Volume() {
        return MA10Volume;
    }

    @Override
    public void setMA10Volume(float v) {
        MA10Volume = v;
    }


    @Override
    public float getAvgPrice() {
        return 0;
    }

    @Override
    public float getPrice() {
        return TextUtils.isEmpty(price) ? 0 : Float.valueOf(price);
    }

    @Override
    public Date getMinuteDate() {
        return new Date(timestamp * 1000);
    }


    @Override
    public float getOpenPrice() {
        return (float) open;
    }

    @Override
    public float getHighPrice() {
        return (float) high;
    }

    @Override
    public float getLowPrice() {
        return (float) low;
    }

    @Override
    public float getClosePrice() {
        return (float) close;
    }

    @Override
    public void setClosePrice(float closePrice) {
        this.close = closePrice;
    }

    @Override
    public float getMA5Price() {
        return MA5Price;
    }

    @Override
    public void setMA5Price(float v) {
        MA5Price = v;
    }

    @Override
    public float getMA10Price() {
        return MA10Price;
    }

    @Override
    public void setMA10Price(float v) {
        MA10Price = v;
    }

    @Override
    public float getMA20Price() {
        return MA20Price;
    }

    @Override
    public void setMA20Price(float v) {
        MA20Price = v;
    }

    @Override
    public long getDatetime() {
        return timestamp * 1000;
    }


    public long getDate() {
        return timestamp * 1000;
    }


    @Override
    public float getUp() {
        return up;
    }

    @Override
    public void setUp(float v) {
        up = v;
    }

    @Override
    public float getMb() {
        return mb;
    }

    @Override
    public void setMb(float v) {
        mb = v;
    }

    @Override
    public float getDn() {
        return dn;
    }

    @Override
    public void setDn(float v) {
        dn = v;
    }

    @Override
    public float getK() {
        return k;
    }

    @Override
    public void setK(float v) {
        k = v;
    }

    @Override
    public float getD() {
        return d;
    }

    @Override
    public void setD(float v) {
        d = v;
    }

    @Override
    public float getJ() {
        return j;
    }

    @Override
    public void setJ(float v) {
        j = v;
    }

    @Override
    public float getDea() {
        return dea;
    }

    @Override
    public void setDea(float v) {
        dea = v;
    }

    @Override
    public float getDif() {
        return dif;
    }

    @Override
    public void setDif(float v) {
        dif = v;
    }

    @Override
    public float getMacd() {
        return macd;
    }

    @Override
    public void setMacd(float v) {
        macd = v;
    }

    @Override
    public float getRsi1() {
        return rsi1;
    }

    @Override
    public float getRsi2() {
        return rsi2;
    }

    @Override
    public float getRsi3() {
        return rsi3;
    }

    @Override
    public void setRsi1(float v) {
        rsi1 = v;
    }

    @Override
    public void setRsi2(float v) {
        rsi2 = v;
    }

    @Override
    public void setRsi3(float v) {
        rsi3 = v;
    }

    @Override
    public float getWr() {
        return wr;
    }

    @Override
    public void setWr(float v) {
        wr = v;
    }

}
