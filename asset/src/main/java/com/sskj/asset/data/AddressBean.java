package com.sskj.asset.data;

import java.util.List;

public class AddressBean {


    private List<Address> btc;
    private List<Address> eth;

    public List<Address> getBtc() {
        return btc;
    }

    public void setBtc(List<Address> btc) {
        this.btc = btc;
    }

    public List<Address> getEth() {
        return eth;
    }

    public void setEth(List<Address> eth) {
        this.eth = eth;
    }

    public static class Address {


        /**
         * id : 22
         * qiaobao_url : 0x644f15C8A9A18BefeEDC75c177dF5d991183b8b7
         * notes : aaafff
         * type : eth
         */

        private String id;
        private String qiaobao_url;
        private String notes;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQiaobao_url() {
            return qiaobao_url;
        }

        public void setQiaobao_url(String qiaobao_url) {
            this.qiaobao_url = qiaobao_url;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

