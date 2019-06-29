package com.sskj.asset.data;

public class WithdrawInfo {

    /**
     * sxfee : 0.0001
     * zz_min : 10
     * zz_max : 100
     * usable : 1000
     */

    private String sxfee;
    private double tb_min;
    private double tb_max;
    private double usable;

    public double getTb_max() {
        return tb_max;
    }

    public void setTb_max(double tb_max) {
        this.tb_max = tb_max;
    }

    public double getTb_min() {
        return tb_min;
    }

    public void setTb_min(double tb_min) {
        this.tb_min = tb_min;
    }

    public String getSxfee() {
        return sxfee;
    }

    public void setSxfee(String sxfee) {
        this.sxfee = sxfee;
    }

    public double getUsable() {
        return usable;
    }

    public void setUsable(double usable) {
        this.usable = usable;
    }
}
