package com.sskj.common.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.sskj.common.R;

public class LoadingProgressDialog extends AppCompatDialog {

    public LoadingProgressDialog(Context context) {
        super(context, R.style.common_custom_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.common_loading_dialog, null);
        setContentView(view);
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams layoutParams= getWindow().getAttributes();
//        int width=(int) (ScreenUtil.getScreenWidth(getContext())*0.3);
//        int height= (int) (ScreenUtil.getScreenWidth(getContext())*0.4);
//        layoutParams.width= width;
//        layoutParams.height=height;
//        getWindow().setAttributes(layoutParams);
    }
}
