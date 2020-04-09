package com.sskj.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.hjq.toast.ToastUtils;
import com.sskj.common.App;
import com.sskj.common.R;


public class CopyUtils {
    public static boolean copy( Context context, String content) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            // 将文本内容放到系统剪贴板里。
            cm.setPrimaryClip(ClipData.newPlainText("", content));
            ToastUtils.show(App.INSTANCE.getString(R.string.common_copyUtils1));
            return true;
        }
        return false;
    }


    public static String getTextFromClip(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if (clipboardManager == null || !clipboardManager.hasPrimaryClip()) {
            return "";
        }
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            return clipData.getItemAt(0).getText().toString();
        }
        return "";
    }
}
