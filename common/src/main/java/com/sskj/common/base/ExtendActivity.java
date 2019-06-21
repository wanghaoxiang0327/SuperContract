package com.sskj.common.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.sskj.common.AppManager;
import com.sskj.common.dialog.LoadingProgressDialog;

public abstract class ExtendActivity extends RefreshActivity implements IBaseView {

    private LoadingProgressDialog loadingDialog;

    private int request;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }

    @Override
    public boolean isEmpty(TextView textView) {
        return textView == null || TextUtils.isEmpty(textView.getText());
    }

    @Override
    public boolean isEmptyShow(TextView textView) {
        if (textView == null) {
            return true;
        } else {
            if (TextUtils.isEmpty(textView.getText())) {
                ToastUtils.show(textView.getHint());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String getText(TextView textView) {
        if (textView != null) {
            return textView.getText().toString();
        }
        return "";
    }

    @Override
    public int color(@ColorRes int id) {
        return ContextCompat.getColor(this, id);
    }

    @Override
    public Drawable drawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(this, id);
    }

    @Override
    public void setText(TextView textView, String text) {
        if (textView != null && text != null) {
            textView.setText(text);
        }
    }

    @Override
    public void showLoading() {
        request++;
        if (request == 1) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingProgressDialog(this);
            }
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

    }

    @Override
    public void hideLoading() {
        request--;
        if (request == 0) {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }
}
