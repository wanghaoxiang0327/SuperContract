package com.sskj.common.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.sskj.common.R;
import com.sskj.common.view.LoadingView;

public class LoadingProgressDialog extends AppCompatDialog {

    LoadingView loadingView;

    public LoadingProgressDialog(Context context) {
        super(context, R.style.common_custom_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.common_loading_dialog, null);
        setContentView(view);
        loadingView = view.findViewById(R.id.loading_view);
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

    @Override
    public void dismiss() {
        super.dismiss();
        loadingView.getIndeterminateDrawable().stop();
    }

}
