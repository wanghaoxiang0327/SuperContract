package com.sskj.contract.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
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
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 忘记密码
 *
 * @author Hey
 * Create at  2019/06/21
 */
public class ForgetPsActivity extends BaseActivity<forgetPsPresenter> {


    @BindView(R2.id.typeTabLayout)
    TabLayout typeTabLayout;
    @BindView(R2.id.account_img)
    ImageView accountImg;
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
    @BindView(R2.id.repeat_ps_edt)
    EditText repeatPsEdt;
    @BindView(R2.id.show_repeat_ps_img)
    ImageView showRepeatPsImg;
    @BindView(R2.id.submit)
    Button submit;


    private ArrayList<CustomTabEntity> typeTabs = new ArrayList<>();
    private RegisterType registerType = RegisterType.MOBILE;
    private DisposableSubscriber<Long> disposableSubscriber;


    @Override
    public int getLayoutId() {
        return R.layout.login_activity_forget_ps;
    }

    @Override
    public forgetPsPresenter getPresenter() {
        return new forgetPsPresenter();
    }

    @Override
    public void initView() {
        typeTabs.add(new TabItem("手机号找回"));
        typeTabs.add(new TabItem("邮箱找回"));
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
    }

    @Override
    public void initData() {
        showPsImg.setOnClickListener(v ->
                EditUtil.togglePs(psEdt, showPsImg)
        );
        showRepeatPsImg.setOnClickListener(v ->
                EditUtil.togglePs(repeatPsEdt, showRepeatPsImg)
        );

        //注册
        ClickUtil.click(submit, view -> {

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


        });

        //发送验证码
        getCodeBtn.setOnClickListener(v -> {
            sendVerifyCodeSuccess();
        });
    }


    /**
     * 切换找回类型
     */
    public void changeType() {
        mobileEdt.getText().clear();
        if (registerType == RegisterType.MOBILE) {
            mobileEdt.setHint(getString(R.string.login_input_mobile));
            mobileEdt.setInputType(InputType.TYPE_CLASS_PHONE);
            accountImg.setImageResource(R.mipmap.login_icon_mobile);
        } else {
            mobileEdt.setHint(getString(R.string.login_input_email));
            mobileEdt.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            accountImg.setImageResource(R.mipmap.login_icon_email);
        }
    }


    /**
     * 发送验证码成功
     */
    public void sendVerifyCodeSuccess() {
        startTimeDown(getCodeBtn);
    }


    public void startTimeDown(TextView getCodeView) {
        getCodeView.setEnabled(false);
        getCodeView.setTextColor(color(R.color.common_hint));
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                if (getCodeView != null) {
                    getCodeView.setText(time + getString(R.string.login_get_code_retry));
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                if (getCodeView != null) {
                    getCodeView.setText(getString(R.string.login_get_verify_code));
                    getCodeView.setEnabled(true);
                    getCodeView.setTextColor(color(R.color.common_white));

                }
                if (!disposableSubscriber.isDisposed()) {
                    disposableSubscriber.dispose();
                    disposableSubscriber = null;
                }

            }
        };

        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableSubscriber!=null){
            disposableSubscriber.dispose();
        }
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
        Intent intent = new Intent(context, ForgetPsActivity.class);
        context.startActivity(intent);
    }


}
