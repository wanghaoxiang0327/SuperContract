package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.router.RoutePath;
import com.sskj.common.utils.ClickUtil;
import com.sskj.mine.data.Verify;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/24
 */
@Route(path = RoutePath.SECURITY)
public class SecurityActivity extends BaseActivity<SecurityPresenter> {


    @BindView(R2.id.menu_sms_verify)
    SuperTextView menuSmsVerify;
    @BindView(R2.id.menu_google_verify)
    SuperTextView menuGoogleVerify;
    @BindView(R2.id.menu_email_verify)
    SuperTextView menuEmailVerify;
    @BindView(R2.id.menu_login_ps)
    SuperTextView menuLoginPs;
    @BindView(R2.id.menu_pay_ps)
    SuperTextView menuPayPs;

    private boolean setPayPs;

    private boolean startGoogle;
    private boolean startSms;

    private boolean bindEmail;
    private boolean bindSMS;
    private boolean bindGoogle;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_security;
    }

    @Override
    public SecurityPresenter getPresenter() {
        return new SecurityPresenter();
    }

    @Override
    public void initView() {

        userViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                setPayPs = TextUtils.isEmpty(userBean.getTpwd());
                if (setPayPs) {
                    menuPayPs.setRightString(getString(R.string.mine_securityActivity1));
                } else {
                    menuPayPs.setRightString(getString(R.string.mine_change));
                }
                if (userBean.getIsBindMail() == 1) {
                    menuEmailVerify.setRightString(getString(R.string.mine_securityActivity2));
                    bindEmail = true;
                } else {
                    bindEmail = false;
                    menuEmailVerify.setRightString(getString(R.string.mine_securityActivity1));
                }

                if (userBean.getIsStartGoogle() == 1) {
                    startGoogle = true;
                    bindGoogle = true;
                    menuGoogleVerify.setRightString(getString(R.string.mine_securityActivity3));
                } else {
                    startGoogle = false;
                    if (userBean.getIsBindGoogle() == 1) {
                        bindGoogle = true;
                        menuGoogleVerify.setRightString(getString(R.string.mine_securityActivity4));
                    } else {
                        bindGoogle = false;
                        menuGoogleVerify.setRightString(getString(R.string.mine_securityActivity1));
                    }
                }
                if (userBean.getIsStartSms() == 1) {
                    startSms = true;
                    bindSMS = true;
                    menuSmsVerify.setRightString(getString(R.string.mine_securityActivity3));
                } else {
                    startSms = false;
                    if (TextUtils.isEmpty(userBean.getMobile())) {
                        bindSMS = false;
                        menuSmsVerify.setRightString(getString(R.string.mine_securityActivity1));
                    } else {
                        bindSMS = true;
                        menuSmsVerify.setRightString(getString(R.string.mine_securityActivity4));
                    }

                }
            }
        });
    }

    @Override
    public void initData() {
        ClickUtil.click(menuSmsVerify, view -> {
            if (!bindSMS) {
                BindMobileOrEmailActivity.start(this, Verify.SMS);
            } else {
                VerifySettingActivity.start(this, Verify.SMS);
            }
        });
        ClickUtil.click(menuEmailVerify, view -> {
            if (bindEmail) {
                VerifySettingActivity.start(this, Verify.EMAIL);
            } else {
                BindMobileOrEmailActivity.start(this, Verify.EMAIL);
            }
        });
        ClickUtil.click(menuGoogleVerify, view -> {
            if (bindGoogle) {
                VerifySettingActivity.start(this, Verify.GOOGLE);
            } else {
                BindGoogleVerifyActivity.start(this);
            }
        });
        ClickUtil.click(menuLoginPs, view -> {
            if (!startSms && !startGoogle) {
                new TipDialog(this)
                        .setContent(getString(R.string.mine_securityActivity5))
                        .setCancelVisible(View.GONE)
                        .show();
            } else {
                ResetPasswordActivity.start(this);
            }
        });
        ClickUtil.click(menuPayPs, view -> {
            if (setPayPs) {
                if (!startSms && !startGoogle) {
                    new TipDialog(this)
                            .setContent(getString(R.string.mine_securityActivity5))
                            .setCancelVisible(View.GONE)
                            .show();
                } else {
                    SettingPasswordActivity.start(this);
                }
            } else {
                ResetPayPasswordActivity.start(this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.update();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SecurityActivity.class);
        context.startActivity(intent);
    }


}
