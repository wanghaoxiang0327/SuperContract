package com.sskj.contract.login;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.sskj.common.CommonConfig;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.VerifyPasswordDialog;
import com.sskj.common.router.RoutePath;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;
import com.sskj.common.tab.TabSelectListener;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.EditUtil;
import com.sskj.common.utils.PatternUtils;
import com.sskj.common.utils.SpUtil;
import com.sskj.contract.login.bean.LoginBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Hey
 * Create at  2019/06/20
 */
@Route(path = RoutePath.LOGIN_LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter> {


    @BindView(R2.id.typeTabLayout)
    TabLayout typeTabLayout;
    @BindView(R2.id.account_img)
    ImageView accountImg;
    @BindView(R2.id.mobile_edt)
    EditText mobileEdt;
    @BindView(R2.id.ps_edt)
    EditText psEdt;
    @BindView(R2.id.show_ps_img)
    ImageView showPsImg;
    @BindView(R2.id.forget_ps)
    TextView forgetPs;
    @BindView(R2.id.submit)
    Button submit;
    @BindView(R2.id.register_tv)
    TextView registerTv;

    private ArrayList<CustomTabEntity> typeTabs = new ArrayList<>();
    private RegisterType registerType = RegisterType.MOBILE;


    @Override
    public int getLayoutId() {
        return R.layout.login_activity_login;
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {
        typeTabs.add(new TabItem("手机号登录"));
        typeTabs.add(new TabItem("邮箱登录"));
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

        String registerText = registerTv.getText().toString();
        SpannableString toRegister = new SpannableString(registerText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                RegisterActivity.start(LoginActivity.this);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(color(R.color.common_tip));
            }
        };
        toRegister.setSpan(clickableSpan, registerText.indexOf("?") + 1, registerText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerTv.setText(toRegister);
        registerTv.setMovementMethod(LinkMovementMethod.getInstance());
        changeType();
    }

    @Override
    public void initData() {
        showPsImg.setOnClickListener(v ->
                EditUtil.togglePs(psEdt, showPsImg)
        );

        //登录
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

            if (isEmptyShow(psEdt)) {
                return;
            }

            if (!PatternUtils.isLoginPs(getText(psEdt))) {
                return;
            }
            mPresenter.isGoogleCheck(mobileEdt.getText().toString(), psEdt.getText().toString());
        });
        //忘记密码
        ClickUtil.click(forgetPs, view -> {
            ForgetPsActivity.start(this);
        });

    }

    /**
     * 切换登录类型
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


    public void loginSuccess(LoginBean loginBean) {
        SpUtil.put(CommonConfig.ACCOUNT, loginBean.getAccount());
        SpUtil.put(CommonConfig.TOKEN, loginBean.getToken());
        if (registerType == RegisterType.MOBILE) {
            SpUtil.put(CommonConfig.MOBILE, getText(mobileEdt));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(CommonConfig.ACCOUNT, loginBean.getAccount());
        httpHeaders.put(CommonConfig.TOKEN, loginBean.getToken());
        OkGo.getInstance().addCommonHeaders(httpHeaders);
        ARouter.getInstance().build(RoutePath.MAIN).navigation();
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.login_status_bar_color)
                //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(false, 0.2f)
                .init();

    }

    /**
     * 显示谷歌验证
     */
    public void showCheckGoogle(String mobile, String opwd) {
        VerifyPasswordDialog verifyPasswordDialog = new VerifyPasswordDialog(this, false, true, false,0);
        verifyPasswordDialog.setOnConfirmListener((dialog, ps, sms, google) -> {
            mPresenter.login(mobile, opwd, google);
        });
        verifyPasswordDialog.show();
    }
}
