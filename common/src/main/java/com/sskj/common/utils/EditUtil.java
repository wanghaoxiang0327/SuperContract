package com.sskj.common.utils;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.sskj.common.R;

public class EditUtil {

    public static boolean togglePs(EditText editText, ImageView imageView) {
        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editText.setSelection(editText.getText().length());
            imageView.setImageResource(R.mipmap.common_icon_show);
            return false;
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setSelection(editText.getText().length());
            imageView.setImageResource(R.mipmap.common_icon_hide);
            return true;
        }
    }

}
