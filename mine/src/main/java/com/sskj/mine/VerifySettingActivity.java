package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.VerifyPasswordDialog;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.view.CheckButton;
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
    CheckButton verifyCheck;

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
                ClickUtil.click(verifyStatus, view -> {
                    BindGoogleVerifyActivity.start(this);
                });
                break;
            default:
                break;
        }

        userViewModel.getUser().observe(this, user -> {
            if (user != null) {
                if (verify == Verify.SMS) {
                    if (TextUtils.isEmpty(user.getMobile())) {
                        verifyStatus.setText("未绑定");
                    } else {
                        verifyStatus.setText("已绑定");
                    }
                    verifyCheck.setChecked(user.getIsStartSms() == 1);
                } else if (verify == Verify.EMAIL) {
                    if (user.getIsBindMail() == 1) {
                        verifyStatus.setText("已绑定");
                    } else {
                        verifyStatus.setText("未绑定");
                    }
                    verifyCheck.setVisibility(View.GONE);
                } else {

                    if (user.getIsBindGoogle() == 1) {
                        verifyStatus.setText("已绑定");
                    } else {
                        verifyStatus.setText("未绑定");
                    }
                    verifyCheck.setChecked(user.getIsStartGoogle() == 1);
                }
            }
        });

    }

    @Override
    public void initData() {
        verifyCheck.setOnSwitchListener(isChecked -> {
            if (verify == Verify.SMS) {
                new VerifyPasswordDialog(this, true, false, false, 3)
                        .setOnConfirmListener((dialog, ps, sms, google) -> {
                            mPresenter.setSmsState(isChecked ? 0 : 1,sms);
                            dialog.dismiss();
                        }).show();
            } else if (verify == Verify.GOOGLE) {
                new VerifyPasswordDialog(this, false, true, false, 0)
                        .setOnConfirmListener((dialog, ps, sms, google) -> {
                            mPresenter.setGoogleState(isChecked ? 0 : 1, google);
                            dialog.dismiss();
                        }).show();
            }
            return false;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.update();
    }

    public static void start(Context context, Verify verify) {
        Intent intent = new Intent(context, VerifySettingActivity.class);
        intent.putExtra("type", verify);
        context.startActivity(intent);
    }


    public void setStateSuccess(boolean ischeck) {
        verifyCheck.setChecked(ischeck);
    }
}
