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

    //=======================交易==============================
    //币种资产
    public static final String COINASSET = "/home/user/wall_list";

    //交易币种
    public static final String TRADECOIN = "/home/Contract/pro_list";

    //创建订单
    public static final String CREATE_ORDER = "/home/Contract/create_order";

    //我的订单
    public static final String MYORDER = "/Home/Contract/contract_record";









    //========================mine================================

    public static final String USER_INFO = "/Home/user/user_info";

    //=========================market=====================================

    public static final String PROFIT_ORDER = "/Home/Contract/income_record";
}
