package com.sskj.market.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

import com.sskj.market.R;

public class CreateOrderDialog extends BottomSheetDialog {

    public CreateOrderDialog(@NonNull Context context) {
        super(context,R.style.BottomSheetDialog);
        setContentView(R.layout.market_dialog_create_order);
        setCancelable(false);
    }
}
