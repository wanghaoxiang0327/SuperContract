package com.sskj.asset.data;

public class TransferRecodsBean {


    /**
     * id : 11
     * pid : 4
     * pname : EOS
     * price : +10.00000000
     * addtime : 1561032827
     * accountId : 52034172
     * memo : 资金转入
     */

    private String id;
    private String pid;
    private String pname;
    private String price;
    private long addtime;
    private String accountId;
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
