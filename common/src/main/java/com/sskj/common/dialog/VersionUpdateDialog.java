package com.sskj.common.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.azhon.appupdate.manager.DownloadManager;
import com.hjq.toast.ToastUtils;
import com.sskj.common.App;
import com.sskj.common.R;
import com.sskj.common.R2;
import com.sskj.common.data.VersionBean;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VersionUpdateDialog extends AlertDialog {

    @BindView(R2.id.update_tip)
    TextView updateTip;
    @BindView(R2.id.update_btn)
    Button updateBtn;
    @BindView(R2.id.cancel_btn)
    Button cancelBtn;
    private View view;

    private VersionBean versionBean;

    public VersionUpdateDialog(Context context, VersionBean versionBean) {
        super(context, R.style.common_custom_dialog);
        view = LayoutInflater.from(context).inflate(R.layout.common_dialog_version_update, null);
        setView(view);
        ButterKnife.bind(this, view);
        this.versionBean = versionBean;
        initView();
        setCancelable(false);
    }

    private void initView() {
        updateTip.setText(versionBean.getContent());
        ClickUtil.click(cancelBtn, view1 -> {
            //强制更新
            if (versionBean.getUptype()==1){
                ToastUtils.show("本次更新很重要，不能取消哦");
                return;
            }else {
                dismiss();
            }
        });

        ClickUtil.click(updateBtn, view1 -> {
            DownloadManager manager = DownloadManager.getInstance(getContext());
            manager.setApkName(App.INSTANCE.getString(R.string.app_name)+".apk")
                    .setApkUrl(versionBean.getAddr())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .download();
            dismiss();
        });

    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.8);
        getWindow().setAttributes(params);
    }
}
