package com.sskj.mine.data;

import java.util.List;

public class TeamBean {


    /**
     * ttl : 3
     * today : 3
     * count : 3
     * p : 1
     * size : 15
     * list : [{"realname":"Jack","mobile":"","mail":"85****66@qq.com","account":"52034175","reg_time":"1561104781"},{"realname":"Mary","mobile":"157****3954","mail":null,"account":"52034174","reg_time":"1561104740"},{"realname":"suny","mobile":"157****3953","mail":null,"account":"52034173","reg_time":"1561104272"}]
     */

    private String ttl;
    private String today;
    private String count;
    private int p;
    private int size;
    private List<Team> list;

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

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

    public List<Team> getList() {
        return list;
    }

    public void setList(List<Team> list) {
        this.list = list;
    }

    public static class Team {
        /**
         * realname : Jack
         * mobile :
         * mail : 85****66@qq.com
         * account : 52034175
         * reg_time : 1561104781
         */

        private String realname;
        private String mobile;
        private String mail;
        private String account;
        private long reg_time;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public long getReg_time() {
            return reg_time;
        }

        public void setReg_time(long reg_time) {
            this.reg_time = reg_time;
        }
    }
}
