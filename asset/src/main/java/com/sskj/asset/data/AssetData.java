package com.sskj.asset.data;

import java.util.List;

public class AssetData {


    /**
     * count : 4
     * p : 1
     * size : 15
     * res : {"total":{"ttl_usable":54282000,"ttl_frost":0,"ttl_money":54282000,"ttl_cnymoney":"374545800.00"},"asset":[{"pid":"1","pname":"USDT","name":getString(R.string.asset_assetData1),"mark":"USDT","usable":"100000.00000000","frost":"0.00000000","uptime":"1560996382","tb_fee_rate":"1","tb_min":"10","tb_max":"100"},{"pid":"2","pname":"BTC","name":getString(R.string.asset_assetData2),"mark":"BTC","usable":"1000.00000000","frost":"0.00000000","uptime":"1560996403","tb_fee_rate":"0.2","tb_min":"20","tb_max":"200"},{"pid":"3","pname":"ETH","name":getString(R.string.asset_assetData3),"mark":"ETH","usable":"10000.00000000","frost":"0.00000000","uptime":"1560996414","tb_fee_rate":"0.8","tb_min":"1","tb_max":"300"},{"pid":"4","pname":"EOS","name":getString(R.string.asset_assetData4),"mark":"EOS","usable":"20000000.00000000","frost":"0.00000000","uptime":"1560996427","tb_fee_rate":"4.1","tb_min":"40","tb_max":"400"}]}
     */

    private String count;
    private int p;
    private int size;
    private ResBean res;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * total : {"ttl_usable":54282000,"ttl_frost":0,"ttl_money":54282000,"ttl_cnymoney":"374545800.00"}
         * asset : [{"pid":"1","pname":"USDT","name":getString(R.string.asset_assetData1),"mark":"USDT","usable":"100000.00000000","frost":"0.00000000","uptime":"1560996382","tb_fee_rate":"1","tb_min":"10","tb_max":"100"},{"pid":"2","pname":"BTC","name":getString(R.string.asset_assetData2),"mark":"BTC","usable":"1000.00000000","frost":"0.00000000","uptime":"1560996403","tb_fee_rate":"0.2","tb_min":"20","tb_max":"200"},{"pid":"3","pname":"ETH","name":getString(R.string.asset_assetData3),"mark":"ETH","usable":"10000.00000000","frost":"0.00000000","uptime":"1560996414","tb_fee_rate":"0.8","tb_min":"1","tb_max":"300"},{"pid":"4","pname":"EOS","name":getString(R.string.asset_assetData4),"mark":"EOS","usable":"20000000.00000000","frost":"0.00000000","uptime":"1560996427","tb_fee_rate":"4.1","tb_min":"40","tb_max":"400"}]
         */

        private TotalBean total;
        private List<AssetBean> asset;

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public List<AssetBean> getAsset() {
            return asset;
        }

        public void setAsset(List<AssetBean> asset) {
            this.asset = asset;
        }

        public static class TotalBean {
            /**
             * ttl_usable : 54282000
             * ttl_frost : 0
             * ttl_money : 54282000
             * ttl_cnymoney : 374545800.00
             */

            private String ttl_usable;
            private String ttl_frost;
            private String ttl_money;
            private String ttl_cnymoney;

            public String getTtl_usable() {
                return ttl_usable;
            }

            public void setTtl_usable(String ttl_usable) {
                this.ttl_usable = ttl_usable;
            }

            public String getTtl_frost() {
                return ttl_frost;
            }

            public void setTtl_frost(String ttl_frost) {
                this.ttl_frost = ttl_frost;
            }

            public String getTtl_money() {
                return ttl_money;
            }

            public void setTtl_money(String ttl_money) {
                this.ttl_money = ttl_money;
            }

            public String getTtl_cnymoney() {
                return ttl_cnymoney;
            }

            public void setTtl_cnymoney(String ttl_cnymoney) {
                this.ttl_cnymoney = ttl_cnymoney;
            }
        }

        public static class AssetBean {
            /**
             * pid : 1
             * pname : USDT
             * name : 泰达币
             * mark : USDT
             * usable : 100000.00000000
             * frost : 0.00000000
             * uptime : 1560996382
             * tb_fee_rate : 1
             * tb_min : 10
             * tb_max : 100
             */

            private String pid;
            private String pname;
            private String name;
            private String mark;
            private String usable;
            private String frost;
            private String uptime;
            private String tb_fee_rate;
            private String tb_min;
            private String tb_max;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getUsable() {
                return usable;
            }

            public void setUsable(String usable) {
                this.usable = usable;
            }

            public String getFrost() {
                return frost;
            }

            public void setFrost(String frost) {
                this.frost = frost;
            }

            public String getUptime() {
                return uptime;
            }

            public void setUptime(String uptime) {
                this.uptime = uptime;
            }

            public String getTb_fee_rate() {
                return tb_fee_rate;
            }

            public void setTb_fee_rate(String tb_fee_rate) {
                this.tb_fee_rate = tb_fee_rate;
            }

            public String getTb_min() {
                return tb_min;
            }

            public void setTb_min(String tb_min) {
                this.tb_min = tb_min;
            }

            public String getTb_max() {
                return tb_max;
            }

            public void setTb_max(String tb_max) {
                this.tb_max = tb_max;
            }
        }
    }
}
