package com.sskj.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取币种小数位
 * @author Hey
 */
public class DigitUtils {


    public static int ASSET_DIGIT=4;

    private static Map<String, Integer> digitMap = new HashMap<>();

    public static int getDigit(String name) {
        if (digitMap.isEmpty()) {
            init();
        }
        name = name.toLowerCase().replace("/", "_");
        return digitMap.get(name) == null ? 4 : digitMap.get(name);
    }

    private static void init() {
        digitMap.put("btc_usdt", 2);
        digitMap.put("ltc_usdt", 2);
        digitMap.put("eth_usdt", 2);
        digitMap.put("etc_usdt", 4);
        digitMap.put("zec_usdt", 2);
        digitMap.put("eos_usdt",4);
        digitMap.put("xrp_usdt",4);
        digitMap.put("trx_usdt",6);
        digitMap.put("dash_usdt", 2);
        digitMap.put("bch_usdt", 2);
    }

}
