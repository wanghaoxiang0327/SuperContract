package com.sskj.mine.data;

import java.util.List;

public class DividendBean {


    /**
     * count : 1
     * p : 1
     * size : 15
     * data : {"list":[{"id":"7","user_id":"171","account":"52034171","inc_USDT":"1.3000","inc_BTC":"0.0000","inc_ETH":"0.0000","inc_EOS":"0.0000","addtime":"1561685242","strtime":"1560700800","endtime":"1561305599","week":"第二十五周"}],"total":[{"code":"USDT","img":"/Uploads/img/2018/12-27/5c24649eac12663091.png","num":1.3},{"code":"BTC","img":"/Uploads/img/2018/12-27/5c24649eac12663091.png","num":0},{"code":"ETH","img":"/Uploads/img/2018/12-27/5c24649eac12663091.png","num":0},{"code":"EOS","img":"/Uploads/img/2018/12-27/5c24649eac12663091.png","num":0}]}
     */

    private String count;
    private int p;
    private int size;
    private Res data;

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

    public Res getData() {
        return data;
    }

    public void setData(Res data) {
        this.data = data;
    }

    public static class Res {
        private List<Dividen> list;
        private List<TotalBean> total;

        public List<Dividen> getList() {
            return list;
        }

        public void setList(List<Dividen> list) {
            this.list = list;
        }

        public List<TotalBean> getTotal() {
            return total;
        }

        public void setTotal(List<TotalBean> total) {
            this.total = total;
        }


        public static class Dividen {
            /**
             * id : 7
             * user_id : 171
             * account : 52034171
             * inc_USDT : 1.3000
             * inc_BTC : 0.0000
             * inc_ETH : 0.0000
             * inc_EOS : 0.0000
             * addtime : 1561685242
             * strtime : 1560700800
             * endtime : 1561305599
             * week : 第二十五周
             */

            private String id;
            private String user_id;
            private String account;
            private String inc_USDT;
            private String inc_BTC;
            private String inc_ETH;
            private String inc_EOS;
            private String addtime;
            private String strtime;
            private String endtime;
            private String week;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getInc_USDT() {
                return inc_USDT;
            }

            public void setInc_USDT(String inc_USDT) {
                this.inc_USDT = inc_USDT;
            }

            public String getInc_BTC() {
                return inc_BTC;
            }

            public void setInc_BTC(String inc_BTC) {
                this.inc_BTC = inc_BTC;
            }

            public String getInc_ETH() {
                return inc_ETH;
            }

            public void setInc_ETH(String inc_ETH) {
                this.inc_ETH = inc_ETH;
            }

            public String getInc_EOS() {
                return inc_EOS;
            }

            public void setInc_EOS(String inc_EOS) {
                this.inc_EOS = inc_EOS;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getStrtime() {
                return strtime;
            }

            public void setStrtime(String strtime) {
                this.strtime = strtime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }
        }

        public static class TotalBean {
            /**
             * code : USDT
             * img : /Uploads/img/2018/12-27/5c24649eac12663091.png
             * num : 1.3
             */

            private String code;
            private String img;
            private String num;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
