package com.sskj.mine.data;

import java.util.List;

public class CommissionBean {

    private int p;
    private int size;
    private int count;
    private Res data;

    public static class Res {
        private List<CommissionTopBean> total;
        private List<CommissionDetailBean> list;
        private Total tongji;

        public Total getTongji() {
            return tongji;
        }

        public void setTongji(Total tongji) {
            this.tongji = tongji;
        }

        public class Total{
            private String ttl_usdt;
            private String ttl_cny;

            public String getTtl_usdt() {
                return ttl_usdt;
            }

            public void setTtl_usdt(String ttl_usdt) {
                this.ttl_usdt = ttl_usdt;
            }

            public String getTtl_cny() {
                return ttl_cny;
            }

            public void setTtl_cny(String ttl_cny) {
                this.ttl_cny = ttl_cny;
            }
        }


        public List<CommissionTopBean> getTotal() {
            return total;
        }

        public void setTotal(List<CommissionTopBean> total) {
            this.total = total;
        }

        public List<CommissionDetailBean> getList() {
            return list;
        }

        public void setList(List<CommissionDetailBean> list) {
            this.list = list;
        }
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Res getData() {
        return data;
    }

    public void setData(Res data) {
        this.data = data;
    }
}
