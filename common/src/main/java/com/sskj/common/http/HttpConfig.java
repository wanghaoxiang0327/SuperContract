package com.sskj.common.http;

import com.sskj.common.BuildConfig;

public class HttpConfig {
    public static final String LOGIN = "/Home/user/user_login";
    public static final String GOOGLE_CHECK = "/Home/User/is_start_google";
    public static final String REGISTER = "/Home/user/register";
    public static final String SEND_SMS = "/Home/user/send_sms";
    public static final String SEND_EMAIL = "/Home/User/send_email";
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
