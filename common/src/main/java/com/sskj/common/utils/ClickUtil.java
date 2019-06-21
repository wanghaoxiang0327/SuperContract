package com.sskj.common.utils;

import android.annotation.SuppressLint;
import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

public class ClickUtil {

    public ClickUtil() {
    }

    @SuppressLint("CheckResult")
    public static void click(View view, ClickUtil.Click click) {
        RxView.clicks(view).throttleFirst(800L, TimeUnit.MILLISECONDS)
                .subscribe((aVoid) -> {
                    click.click(view);
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    @SuppressLint("CheckResult")
    public static void click(long time, View view, ClickUtil.Click click) {
        RxView.clicks(view).throttleFirst(time, TimeUnit.MILLISECONDS).subscribe((aVoid) -> {
            click.click(view);
        },throwable -> {
            throwable.printStackTrace();
        });
    }

    public interface Click {
        void click(View view);
    }
}
