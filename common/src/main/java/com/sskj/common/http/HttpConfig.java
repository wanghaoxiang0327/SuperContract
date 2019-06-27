package com.sskj.common.http;

import com.sskj.common.BuildConfig;

public class HttpConfig {
    public static String BASE_URL = BuildConfig.IP;


    //==================================================

    /**
     * 行情数据
     */
    public static final String GET_PRODUCT = "/home/ajax/getpro";


    //========================mine================================

    public static final String USER_INFO = "/Home/user/user_info";

    //=========================market=====================================

    public static final String PROFIT_ORDER = "/Home/Contract/income_record";
}
