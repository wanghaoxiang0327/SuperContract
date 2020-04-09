package com.sskj.common.view;


import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.hjq.toast.ToastUtils;

public class CheckButton extends AppCompatCheckBox {


    public CheckButton(Context context) {
        super(context);
    }

    public CheckButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        if (onSwitchListener != null) {
            if (onSwitchListener.onSwitch(isChecked())) {
                super.toggle();
            }
        } else {
            super.toggle();
        }
    }


    public interface OnSwitchListener {

        boolean onSwitch(boolean isChecked);
    }


    OnSwitchListener onSwitchListener;


    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        this.onSwitchListener = onSwitchListener;
    }
}
