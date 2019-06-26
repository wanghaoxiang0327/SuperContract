package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;
import com.sskj.mine.data.Verify;

import butterknife.BindView;

/**
 * 绑定手机号或邮箱
 *
 * @author Hey
 * Create at  2019/06/25
 */
public class BindMobileOrEmailActivity extends BaseActivity<BindMobileOrEmailPresenter> {


    @BindView(R2.id.verify_name)
    TextView verifyName;
    @BindView(R2.id.verify_account_edt)
    EditText verifyAccountEdt;
    @BindView(R2.id.verify_code_name)
    TextView verifyCodeName;
    @BindView(R2.id.edt_verify_code)
    EditText edtVerifyCode;

    Verify verify;
    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_bind_mobile_or_email;
    }

    @Override
    public BindMobileOrEmailPresenter getPresenter() {
        return new BindMobileOrEmailPresenter();
    }

    @Override
    public void initView() {
        verify= (Verify) getIntent().getSerializableExtra("type");
        verifyName.setText(verify.getName());
        switch (verify){
            case EMAIL:
                verifyCodeName.setText("邮箱验证码");
                break;
            case SMS:
                verifyCodeName.setText("手机验证码");
                break;
        }

    }

    @Override
    public void initData() {

    }

    public static void start(Context context, Verify verify) {
        Intent intent = new Intent(context, BindMobileOrEmailActivity.class);
        intent.putExtra("type", verify);
        context.startActivity(intent);
    }


}
