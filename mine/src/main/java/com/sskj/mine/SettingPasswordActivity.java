package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hjq.toast.ToastUtils;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.VerifyPasswordDialog;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.EditUtil;
import com.sskj.common.utils.PatternUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置支付密码
 *
 * @author Hey
 * Create at  2019/06/25
 */
public class SettingPasswordActivity extends BaseActivity<SettingPasswordPresenter> {

    @BindView(R2.id.ps_edt)
    EditText psEdt;
    @BindView(R2.id.show_ps_img)
    ImageView showPsImg;
    @BindView(R2.id.ps_repeat_edt)
    EditText psRepeatEdt;
    @BindView(R2.id.show_repeat_ps_img)
    ImageView showRepeatPsImg;
    @BindView(R2.id.submit)
    Button submit;

    boolean checkSms;
    boolean checkGoogle;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_setting_password;
    }

    @Override
    public SettingPasswordPresenter getPresenter() {
        return new SettingPasswordPresenter();
    }

    @Override
    public void initView() {
        userViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                checkSms = userBean.getIsStartSms() == 1;
                checkGoogle = userBean.getIsStartGoogle() == 1;
            }
        });
    }

    @Override
    public void initData() {

        showPsImg.setOnClickListener(v -> {
            EditUtil.togglePs(psEdt, showPsImg);
        });

        showRepeatPsImg.setOnClickListener(v -> {
            EditUtil.togglePs(psRepeatEdt, showRepeatPsImg);
        });

        ClickUtil.click(submit, view -> {
            if (isEmptyShow(psEdt)) {
                return;
            }

            if (isEmptyShow(psRepeatEdt)) {
                return;
            }

            if (!PatternUtils.isLoginPs(getText(psEdt))) {
                return;
            }

            if (!getText(psRepeatEdt).equals(getText(psEdt))) {
                ToastUtils.show(getString(R.string.mine_resetPasswordActivity2));
                return;
            }

            if (checkSms || checkGoogle) {
                new VerifyPasswordDialog(this, checkSms, checkGoogle, false,4)
                        .setOnConfirmListener((dialog, ps, sms, google) -> {
                            mPresenter.resetLoginPs(getText(psEdt), getText(psRepeatEdt), sms, google);
                        }).show();
            } else {
                mPresenter.resetLoginPs(getText(psEdt), getText(psRepeatEdt), "", "");
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingPasswordActivity.class);
        context.startActivity(intent);
    }


    public void setPsSuccess() {

        finish();
    }
}
