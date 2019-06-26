package com.sskj.mine.data;

public enum Verify {
    SMS("手机号码","短信验证"),GOOGLE("谷歌身份验证器","谷歌验证"),EMAIL("邮箱地址","邮箱验证");

    String name;
    String type;

    Verify(String name,String type){
        this.name=name;
        this.type=type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
