package com.sskj.common.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

public class LocalManageUtil {

    private static final String TAG = "LocalManageUtil";

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context) {
        return LanguageSPUtil.getInstance(context).getSystemCurrentLocal();
    }


    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        switch (LanguageSPUtil.getInstance(context).getSelectLanguage()) {
            case 1:
                return Locale.SIMPLIFIED_CHINESE;
            case 2:
                return Locale.TRADITIONAL_CHINESE;
            case 3:
                return Locale.ENGLISH;
            case 4:
                return Locale.KOREA;
            default:
                return Locale.SIMPLIFIED_CHINESE;
        }
    }



    public static String getLanguage(Context context){
        switch (LanguageSPUtil.getInstance(context).getSelectLanguage()) {
            case 1:
                return "zh";
            case 2:
                return "fan";
            case 3:
                return "en";
            case 4:
                return "han";
            default:
                return "zh";
        }
    }

    public static void saveSelectLanguage(Context context, int select) {
        LanguageSPUtil.getInstance(context).saveLanguage(select);
        setApplicationLanguage(context);
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    public static void saveSystemCurrentLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        Log.d(TAG, locale.getLanguage());
        LanguageSPUtil.getInstance(context).setSystemCurrentLocal(locale);
    }

    public static void onConfigurationChanged(Context context){
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }
}
