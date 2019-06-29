package com.sskj.common.utils;

import com.sskj.common.R;

import java.util.HashMap;
import java.util.Map;

public class CoinIcon {

    private static Map<String, Integer> iconMap = new HashMap<>();

    public static int getIcon(String name) {
        if (iconMap.isEmpty()) {
            init();
        }
        name = name.toLowerCase().replace("/", "_");
        return iconMap.get(name) == null ? 0 : iconMap.get(name);
    }

    private static void init() {
        iconMap.put("btc_usdt", R.mipmap.common_icon_btc);
        iconMap.put("btc", R.mipmap.common_icon_btc);
        iconMap.put("eos_usdt", R.mipmap.common_icon_eos);
        iconMap.put("eos", R.mipmap.common_icon_eos);
        iconMap.put("eth_usdt", R.mipmap.common_icon_eth);
        iconMap.put("eth", R.mipmap.common_icon_eth);
        iconMap.put("bch_usdt", R.mipmap.common_icon_bch);
        iconMap.put("bch", R.mipmap.common_icon_bch);
//        iconMap.put("xrp_usdt", R.mipmap.app_icon_xrp);
//        iconMap.put("etc_usdt", R.mipmap.app_icon_etc);
//        iconMap.put("ltc_usdt", R.mipmap.app_icon_ltc);
//        iconMap.put("qtum_usdt", R.mipmap.app_icon_qtum);
//        iconMap.put("ada_usdt", R.mipmap.app_icon_ada);
//        iconMap.put("xmr_usdt", R.mipmap.app_icon_xmr);
//        iconMap.put("bnb_usdt", R.mipmap.app_icon_bnb);
//        iconMap.put("pt_usdt", R.mipmap.app_icon_pt);


    }

}
