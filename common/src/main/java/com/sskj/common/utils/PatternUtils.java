package com.sskj.common.utils;

import com.hjq.toast.ToastUtils;
import com.sskj.common.App;
import com.sskj.common.R;

/**
 * 正则工具类
 *
 * @author Hey
 */
public class PatternUtils {



    public static boolean isLoginPs(String text) {
        String regex =  "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
        if (!text.matches(regex)) {
            ToastUtils.show(App.INSTANCE.getString(R.string.common_patternUtils1));
            return false;
        }

        return true;
    }

    public static boolean isPayPs(String text) {
        String regex =  "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
        if (!text.matches(regex)) {
            ToastUtils.show(App.INSTANCE.getString(R.string.common_patternUtils1));
            return false;
        }

        return true;
    }

    public static boolean isMobile(String text) {
        String regex = "^0?(13|14|15|16|17|18|19)[0-9]{9}$";

        if (!text.matches(regex)) {
            ToastUtils.show(App.INSTANCE.getString(R.string.common_patternUtils2));
            return false;
        }

        return true;
    }

    public static boolean isEmail(String text) {
        String regex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!text.matches(regex)) {
            ToastUtils.show(App.INSTANCE.getString(R.string.common_patternUtils3));
            return false;
        }
        return true;
    }

    public static boolean isRealName(String text) {

        String regex = "^[A-Za-z\\u4e00-\\u9fa5]+$";
        if (!text.matches(regex)) {
            ToastUtils.show(App.INSTANCE.getString(R.string.common_patternUtils4));
            return false;
        }
        return true;
    }

}
