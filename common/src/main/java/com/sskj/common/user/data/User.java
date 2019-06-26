package com.sskj.common.user.data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {

    @PrimaryKey
    private int id;


    /**
     * uid : 52034171
     * mobile : 15738733951
     * mail :
     * nickname : 山人
     * tgno : AKG52034171
     * user_level : 1
     * register_type : 1
     * is_start_google : 0
     * is_start_sms : 1
     * is_bd_mail : 0
     * zc_total : {"ttl_usable":5.4466040608038E7,"ttl_frost":0,"ttl_money":5.4466040608038E7,"ttl_cnymoney":"375815680.20"}
     */

    private String uid;
    private String mobile;
    private String mail;
    private String nickname;
    private String tgno;
    @SerializedName("user_level")
    private int userLevel;
    @SerializedName("register_type")
    private String registerType;
    //是否开启谷歌验证 0关  1开
    @SerializedName("is_start_google")
    private int isStartGoogle;
    //短信验证  0关  1开
    @SerializedName("is_start_sms")
    private int isStartSms;
    //是否绑定邮箱
    @SerializedName("is_bd_mail")
    private int isBindMail;
    @SerializedName("zc_total")
    private ZcTotalBean zcTotal;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTgno() {
        return tgno;
    }

    public void setTgno(String tgno) {
        this.tgno = tgno;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public int getIsStartGoogle() {
        return isStartGoogle;
    }

    public void setIsStartGoogle(int isStartGoogle) {
        this.isStartGoogle = isStartGoogle;
    }

    public int getIsStartSms() {
        return isStartSms;
    }

    public void setIsStartSms(int isStartSms) {
        this.isStartSms = isStartSms;
    }

    public int getIsBindMail() {
        return isBindMail;
    }

    public void setIsBindMail(int isBindMail) {
        this.isBindMail = isBindMail;
    }

    public ZcTotalBean getZcTotal() {
        return zcTotal;
    }

    public void setZcTotal(ZcTotalBean zcTotal) {
        this.zcTotal = zcTotal;
    }

    public static class ZcTotalBean {
        /**
         * ttl_usable : 5.4466040608038E7
         * ttl_frost : 0
         * ttl_money : 5.4466040608038E7
         * ttl_cnymoney : 375815680.20
         */

        private double ttl_usable;
        private int ttl_frost;
        private double ttl_money;
        private String ttl_cnymoney;

        public double getTtl_usable() {
            return ttl_usable;
        }

        public void setTtl_usable(double ttl_usable) {
            this.ttl_usable = ttl_usable;
        }

        public int getTtl_frost() {
            return ttl_frost;
        }

        public void setTtl_frost(int ttl_frost) {
            this.ttl_frost = ttl_frost;
        }

        public double getTtl_money() {
            return ttl_money;
        }

        public void setTtl_money(double ttl_money) {
            this.ttl_money = ttl_money;
        }

        public String getTtl_cnymoney() {
            return ttl_cnymoney;
        }

        public void setTtl_cnymoney(String ttl_cnymoney) {
            this.ttl_cnymoney = ttl_cnymoney;
        }
    }
}
