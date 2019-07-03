package com.sskj.mine.data;

public class CommissionDetailBean {

    /**
     * down_account : 52034182
     * fee : 0.0023
     * addtime : 1561452455
     */

    private String down_account;
    private String fee;
    private long addtime;
    private String pname;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDown_account() {
        return down_account;
    }

    public void setDown_account(String down_account) {
        this.down_account = down_account;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }
}
