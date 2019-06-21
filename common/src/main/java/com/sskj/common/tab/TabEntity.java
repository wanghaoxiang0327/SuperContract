package com.sskj.common.tab;

import android.support.annotation.DrawableRes;

public interface TabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}
