package com.sskj.contract.login;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.sskj.common.CommonConfig;
import com.sskj.common.WebActivity;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;
import com.sskj.common.tab.TabSelectListener;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.EditUtil;
import com.sskj.common.utils.PatternUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author Hey
 * Create at  2019/06/21
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> {

    @BindView(R2.id.typeTabLayout)
    TabLayout typeTabLayout;
    @BindView(R2.id.name_edt)
    EditText nameEdt;
    @BindView(R2.id.mobile_edt)
    EditText mobileEdt;
    @BindView(R2.id.verify_code_edt)
    EditText verifyCodeEdt;
    @BindView(R2.id.get_code_btn)
    TextView getCodeBtn;
    @BindView(R2.id.ps_edt)
    EditText psEdt;
    @BindView(R2.id.show_ps_img)
    ImageView showPsImg;
    @BindView(R2.id.account_img)
    ImageView accoutImg;
    @BindView(R2.id.repeat_ps_edt)
    EditText repeatPsEdt;
    @BindView(R2.id.show_repeat_ps_img)
    ImageView showRepeatPsImg;
    @BindView(R2.id.invite_code_edt)
    EditText inviteCodeEdt;
    @BindView(R2.id.submit)
    Button submit;
    @BindView(R2.id.read_rules)
    CheckBox readRules;

    private ArrayList<CustomTabEntity> typeTabs = new ArrayList<>();
    private RegisterType registerType = RegisterType.MOBILE;
    private DisposableSubscriber<Long> disposableSubscriber;

    @Override
    public int getLayoutId() {
        return R.layout.login_activity_register;
    }

    @Override
    public RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void initView() {
        typeTabs.add(new TabItem("手机号注册"));
        typeTabs.add(new TabItem("邮箱注册"));
        typeTabLayout.setTabData(typeTabs);
        typeTabLayout.setOnTabSelectListener(new TabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                if (position == 0) {
                    registerType = RegisterType.MOBILE;
                } else {
                    registerType = RegisterType.EMAIL;
                }
                changeType();
                return true;
            }

            @Override
            public boolean onTabReselect(int position) {
                return true;
            }
        });
        changeType();
    }

    @Override
    public void initData() {
        showPsImg.setOnClickListener(v ->
                EditUtil.togglePs(psEdt, showPsImg)
        );
        showRepeatPsImg.setOnClickListener(v ->
                EditUtil.togglePs(repeatPsEdt, showRepeatPsImg)
        );

        SpannableString reg=new SpannableString("已阅读并同意《用户注册协议》");
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                WebActivity.start(RegisterActivity.this,1);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {

            }
        };
        reg.setSpan(clickableSpan, reg.toString().indexOf("《"), reg.toString().indexOf("》"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        readRules.setText(reg);
        readRules.setMovementMethod(new LinkMovementMethod());

        //注册
        ClickUtil.click(submit, view -> {
            if (isEmptyShow(nameEdt)) {
                return;
            }
            if (isEmptyShow(mobileEdt)) {
                return;
            }

            if (registerType == RegisterType.MOBILE) {
                if (!PatternUtils.isMobile(getText(mobileEdt))) {
                    return;
                }
            } else {
                if (!PatternUtils.isEmail(getText(mobileEdt))) {
                    return;
                }
            }

            if (isEmptyShow(verifyCodeEdt)) {
                return;
            }

            if (isEmptyShow(psEdt)) {
                return;
            }

            if (!PatternUtils.isLoginPs(getText(psEdt))) {
                return;
            }

            if (isEmptyShow(repeatPsEdt)) {
                return;
            }

            if (!getText(psEdt).equals(getText(repeatPsEdt))) {
                ToastUtils.show("密码输入不一致");
                return;
            }

            if (isEmptyShow(inviteCodeEdt)) {
                return;
            }
            if (!readRules.isChecked()) {
                ToastUtils.show("请先阅读并同意《用户注册协议》");
                return;
            }
            mPresenter.register(mobileEdt.getText().toString(),
                    nameEdt.getText().toString(),
                    verifyCodeEdt.getText().toString(),
                    psEdt.getText().toString(),
                    repeatPsEdt.getText().toString(),
                    inviteCodeEdt.getText().toString()
            );

        });

        //发送验证码
        getCodeBtn.setOnClickListener(v -> {
            if (registerType == RegisterType.MOBILE) {
                if (!PatternUtils.isMobile(getText(mobileEdt))) {
                    return;
                }
            } else {
                if (!PatternUtils.isEmail(getText(mobileEdt))) {
                    return;
                }
            }
            if (registerType == RegisterType.EMAIL) {

                mPresenter.sendEmail(mobileEdt.getText().toString());
            } else {
                mPresenter.sendSms(mobileEdt.getText().toString());

            }
        });
    }


    /**
     * 切换注册类型
     */
    public void changeType() {
        mobileEdt.getText().clear();
        if (registerType == RegisterType.MOBILE) {
            mobileEdt.setHint(getString(R.string.login_input_mobile));
            mobileEdt.setInputType(InputType.TYPE_CLASS_PHONE);
            accoutImg.setImageResource(R.mipmap.login_icon_mobile);
        } else {
            mobileEdt.setHint(getString(R.string.login_input_email));
            mobileEdt.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            accoutImg.setImageResource(R.mipmap.login_icon_email);
        }
    }

    /**
     * 发送验证码成功
     */
    public void sendVerifyCodeSuccess() {
        startTimeDown(getCodeBtn);
    }




    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.login_status_bar_color)
                //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(false, 0.2f)
                .init();

    }



    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public void registerSuccess(String mobile) {
        Intent intent = new Intent();
        intent.putExtra(CommonConfig.MOBILE, mobile);
        setResult(RESULT_OK, intent);
        finish();
    }
}
