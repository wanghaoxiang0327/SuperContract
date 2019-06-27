package com.sskj.contract.login;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.contract.login.RegisterActivity;


/**
 * @author Hey
 * Create at  2019/06/21
 */
public class RegisterPresenter extends BasePresenter<RegisterActivity> {

    public void register(String mobile, String realname, String code, String opwd, String opwd1, String tjuser) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.REGISTER)
                .params("mobile", mobile)
                .params("realname", realname)
                .params("code", code)
                .params("opwd", opwd)
                .params("opwd1", opwd1)
                .params("tjuser", tjuser)
                .execute(new JsonCallBack<HttpResult>(this) {
                    @Override
                    protected void onNext(HttpResult result) {
                        mView.registerSuccess(mobile);
                    }
                });
    }

    /**
     *
     * @param mobile type 1注册 （2 重置 3 安全验证 4 资金密码设置 5 提币）
     * @param
     */
    public void sendSms(String mobile) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.SEND_SMS)
                .params("mobile", mobile)
                .params("type", 1)
                .execute(new JsonCallBack<HttpResult>(this) {
                    @Override
                    protected void onNext(HttpResult result) {
                        mView.sendVerifyCodeSuccess();
                    }
                });
    }

    public void sendEmail(String email) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.SEND_EMAIL)
                .params("email", email)
                .execute(new JsonCallBack<HttpResult>(this) {
                    @Override
                    protected void onNext(HttpResult result) {
                        mView.sendVerifyCodeSuccess();
                    }
                });
    }
}
