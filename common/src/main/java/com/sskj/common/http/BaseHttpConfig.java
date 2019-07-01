package com.sskj.common.http;

import com.sskj.common.BuildConfig;

public class BaseHttpConfig {

    public static final int OK = 200;
    public static final int LOGOUT = 404;

    public static String BASE_URL = BuildConfig.IP;
    public static String WS_URL = "ws://47.244.174.166:7272";
    public static String WS_PROFIT = "ws://47.244.174.166:7273";


}
