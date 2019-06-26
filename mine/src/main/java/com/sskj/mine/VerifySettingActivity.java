package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;
import com.sskj.common.utils.ClickUtil;
import com.sskj.mine.data.Verify;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置验证方式
 *
 * @author Hey
 * Create at  2019/06/25
 */
public class VerifySettingActivity extends BaseActivity<VerifySettingPresenter> {

    Verify verify;
    @BindView(R2.id.verify_name)
    TextView verifyName;
    @BindView(R2.id.verify_account)
    TextView verifyAccount;
    @BindView(R2.id.verify_status)
    TextView verifyStatus;
    @BindView(R2.id.verify_check)
    CheckBox verifyCheck;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_verify_setting;
    }

    @Override
    public VerifySettingPresenter getPresenter() {
        return new VerifySettingPresenter();
    }


    @Override
    public void initView() {
        verify = (Verify) getIntent().getSerializableExtra("type");
        verifyName.setText(verify.getName());
        verifyCheck.setText(verify.getType());
        switch (verify) {
            case SMS:
                ClickUtil.click(verifyStatus, view -> {
                    BindMobileOrEmailActivity.start(this, verify);
                });
                break;
            case EMAIL:
                ClickUtil.click(verifyStatus, view -> {
                    BindMobileOrEmailActivity.start(this, verify);
                });
                break;
            case GOOGLE:
                BindGoogleVerifyActivity.start(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {

    }

    public static void start(Context context, Verify verify) {
        Intent intent = new Intent(context, VerifySettingActivity.class);
        intent.putExtra("type", verify);
        context.startActivity(intent);
    }


}
