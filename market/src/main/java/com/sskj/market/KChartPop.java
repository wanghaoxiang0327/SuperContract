package com.sskj.market;

import android.content.Context;
import android.view.View;

import razerdp.basepopup.BasePopupWindow;

public class KChartPop extends BasePopupWindow {
    public KChartPop(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.market_pop_kchart);
    }
}
