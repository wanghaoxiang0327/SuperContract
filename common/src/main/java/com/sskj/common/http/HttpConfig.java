package com.sskj.common.http;

import com.sskj.common.BuildConfig;

public class HttpConfig {

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

    public static final String BIND_EMAIL = "/Home/user/binding_email";

    public static final String BIND_MOBILE = "/Home/user/binding_mobile";

    public static final String BIND_GOOGLE = "/home/google/checkGoogleCommand";


    public static final String LOGIN = "/Home/user/user_login";

    public static final String GOOGLE_CHECK = "/Home/user/is_start_google";

    public static final String REGISTER = "/Home/user/register";

    public static final String SEND_SMS = "/Home/user/send_sms";

    public static final String SEND_EMAIL = "/Home/user/send_email";

    public static final String RESET_LOGIN_PS = "/Home/user/xiugai_pwd";

    public static final String RESET_PAY_PS = "/Home/user/reset_tpwd";

    public static final String SET_SMS_STATE = "/Home/User/sms_check";

    public static final String SET_GOOGLE_STATE = "/home/google/set_google_state";

    public static final String GET_GOOGLE_INFO = "/Home/Google/createGoogleCommand";





    //=========================market=====================================

    public static final String PROFIT_ORDER = "/Home/Contract/income_record";


    //==========================Asset==========================================

    public static final String ASSETLIST = "/Home/user/get_asset";

    public static final String ASSET_RECORDS = "/Home/user/re_asset";

    public static final String ASSET_TYPE = "/Home/user/caiwu_type";

    public static final String RECHARGE_INFO = "/Home/Users/bpay";

    public static final String TRANSFER_INFO = "/home/users/bTranfer";

    public static final String TRANSFER = "/home/users/tranfer";

    public static final String TRANSFER_RECORD = "/Home/users/tranfer_record";

    public static final String WITHDRAW_INFO = "/home/users/get_sxfee";

    public static final String WITHDRAW = "/Home/Users/ti_bi";


    public static final String ADDRESS_LIST = "/Home/Users/AddrList";

    public static final String ADDRESS_MANAGE = "/Home/Users/AddrManage";









}
