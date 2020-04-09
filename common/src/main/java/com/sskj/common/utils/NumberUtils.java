package com.sskj.common.utils;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberUtils {

    public static String keep2(Object num) {
        return keepDown(num, 2);
    }

    public static String keep4(Object num) {
        return keepDown(num, 4);
    }

    public static String keep6(Object num) {
        return keepDown(num, 6);
    }

    public static String keepMaxUp(Object num, int maxDigit) {

        return format(num, 0, maxDigit, RoundingMode.HALF_UP);
    }

    public static String keepMaxDown(Object num, int digit) {

        return format(num, 0, digit, RoundingMode.DOWN);
    }


    public static String keepDown(Object num, int digit) {
        return format(num, 0, digit, RoundingMode.DOWN);
    }


    public static String keepUp(Object num, int digit) {
        return format(num, digit, digit, RoundingMode.HALF_UP);
    }


    public static String format(Object num, int minDigit, int maxDigit, RoundingMode mode) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(mode);
        numberFormat.setMinimumFractionDigits(minDigit);
        numberFormat.setMaximumFractionDigits(maxDigit);
        if (num instanceof String) {
            String str = (String) num;
            if (TextUtils.isEmpty(str)) {
                str="0";
            }
            if (!isNumeric(str)) {
                return str;
            }
            return numberFormat.format(Double.parseDouble(str));
        } else if (num instanceof Double || num instanceof Float || num instanceof Long) {
            return numberFormat.format(num);
        }
        return "0";
    }


    public static boolean isNumeric(String str) {
        String reg = "-?[0-9]+.*[0-9]*";
        return str.matches(reg);
    }
}
