package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.TipDialog;
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
    @BindView(R2.id.verify_check_text)
    TextView verifyCheckText;

    private boolean startGoogle;
    private boolean startSms;

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
        verifyCheckText.setText(verify.getType());
        switch (verify) {
            case SMS:
                mToolBarLayout.setTitle(getString(R.string.mine_verify2));
                break;
            case EMAIL:

                mToolBarLayout.setTitle(getString(R.string.mine_verify6));
                break;
            case GOOGLE:
                ClickUtil.click(verifyStatus, view -> {
                    BindGoogleVerifyActivity.start(this);
                });
                mToolBarLayout.setTitle(getString(R.string.mine_verify4));
                break;
            default:
                break;
        }

        userViewModel.getUser().observe(this, user -> {
            if (user != null) {
                startGoogle = user.getIsStartGoogle() == 1;
                startSms = user.getIsStartSms() == 1;
                if (verify == Verify.SMS) {
                    if (TextUtils.isEmpty(user.getMobile())) {
                        verifyStatus.setText(getString(R.string.mine_verifySettingActivity1));
                        ClickUtil.click(verifyStatus, view -> {
                            BindMobileOrEmailActivity.start(this, verify);
                        });
                    } else {
                        verifyStatus.setText(getString(R.string.mine_verifySettingActivity2));
                        ClickUtil.click(verifyStatus, view -> {

                        });
                    }
                    verifyCheck.setChecked(user.getIsStartSms() == 1);
                } else if (verify == Verify.EMAIL) {
                    if (user.getIsBindMail() == 1) {
                        ClickUtil.click(verifyStatus, view -> {
                        });
                        verifyStatus.setText(getString(R.string.mine_verifySettingActivity2));
                    } else {
                        ClickUtil.click(verifyStatus, view -> {
                            BindMobileOrEmailActivity.start(this, verify);
                        });
                        verifyStatus.setText(getString(R.string.mine_verifySettingActivity1));
                    }
                    verifyCheckText.setVisibility(View.GONE);
                    verifyCheck.setVisibility(View.GONE);
                } else {

                    if (user.getIsBindGoogle() == 1) {
                        verifyStatus.setText(getString(R.string.mine_verifySettingActivity2));
                    } else {
                        verifyStatus.setText(getString(R.string.mine_verifySettingActivity1));
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
                //谷歌和短信必须开启一种
                if (isChecked && !startGoogle) {
                    new TipDialog(this)
                            .setContent(getString(R.string.mine_securityActivity5))
                            .setCancelVisible(View.GONE)
                            .show();
                } else {
                    new VerifyPasswordDialog(this, true, false, false, 3)
                            .setOnConfirmListener((dialog, ps, sms, google) -> {
                                mPresenter.setSmsState(isChecked ? 0 : 1, sms);
                                dialog.dismiss();
                            }).show();
                }


            } else if (verify == Verify.GOOGLE) {
                //谷歌和短信必须开启一种
                if (isChecked && !startSms) {
                    new TipDialog(this)
                            .setContent(getString(R.string.mine_securityActivity5))
                            .setCancelVisible(View.GONE)
                            .show();
                } else {
                    new VerifyPasswordDialog(this, false, true, false, 0)
                            .setOnConfirmListener((dialog, ps, sms, google) -> {
                                mPresenter.setGoogleState(isChecked ? 0 : 1, google);
                                dialog.dismiss();
                            }).show();
                }

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
