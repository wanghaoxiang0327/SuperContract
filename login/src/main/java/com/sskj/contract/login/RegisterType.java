package com.sskj.contract.login;

/**
 * 注册类型
 */
public enum RegisterType {
    /**
     * 手机号注册
     */
    MOBILE(0),
    /**
     * 邮箱注册
     */
    EMAIL(1);

    private int type;

    RegisterType(int type) {
        this.type = type;
    }

}