package com.sskj.asset.data;

public class TransferInfo {

    /**
     * sxfee : 0.0001
     * zz_min : 10
     * zz_max : 100
     * usable : 1000
     */

    private String sxfee;
    private double zz_min;
    private double zz_max;
    private double usable;

    public String getSxfee() {
        return sxfee;
    }

    public void setSxfee(String sxfee) {
        this.sxfee = sxfee;
    }

    public double getZz_min() {
        return zz_min;
    }

    public void setZz_min(double zz_min) {
        this.zz_min = zz_min;
    }

    public double getZz_max() {
        return zz_max;
    }

    public void setZz_max(double zz_max) {
        this.zz_max = zz_max;
    }

    public double getUsable() {
        return usable;
    }

    public void setUsable(double usable) {
        this.usable = usable;
    }
}
