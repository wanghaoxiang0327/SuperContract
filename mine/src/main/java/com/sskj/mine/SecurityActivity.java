package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.allen.library.SuperTextView;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.utils.ClickUtil;
import com.sskj.mine.data.Verify;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/24
 */
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

    }

    @Override
    public void initData() {
        ClickUtil.click(menuSmsVerify, view -> {
            VerifySettingActivity.start(this, Verify.SMS);
        });
        ClickUtil.click(menuEmailVerify, view -> {
            VerifySettingActivity.start(this, Verify.EMAIL);
        });
        ClickUtil.click(menuGoogleVerify, view -> {
            VerifySettingActivity.start(this, Verify.GOOGLE);
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SecurityActivity.class);
        context.startActivity(intent);
    }


}
