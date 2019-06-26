package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sskj.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改登录密码
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class ResetPasswordActivity extends BaseActivity<ResetPasswordPresenter> {


    @BindView(R2.id.ps_edt)
    EditText psEdt;
    @BindView(R2.id.new_ps_edt)
    EditText newPsEdt;
    @BindView(R2.id.show_new_ps_img)
    ImageView showNewPsImg;
    @BindView(R2.id.ps_repeat_edt)
    EditText psRepeatEdt;
    @BindView(R2.id.show_repeat_ps_img)
    ImageView showRepeatPsImg;
    @BindView(R2.id.submit)
    Button submit;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_reset_password;
    }

    @Override
    public ResetPasswordPresenter getPresenter() {
        return new ResetPasswordPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
