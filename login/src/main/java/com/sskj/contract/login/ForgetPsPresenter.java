package com.sskj.contract.login;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;


/**
 * @author Hey
 * Create at  2019/06/21
 */
public class ForgetPsPresenter extends BasePresenter<ForgetPsActivity> {
    /**
     *
     * @param mobile type 1注册 （2 重置 3 安全验证 4 资金密码设置 5 提币）
     * @param
     */
    public void sendSms(String mobile) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.SEND_SMS)
                .params("mobile", mobile)
                .params("type", 2)
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
    public void forgetPs(String mobile,String code,String opwd,String opwd1) {
        OkGo.<HttpResult>post(HttpConfig.BASE_URL + HttpConfig.FORGET_PS)
                .params("mobile", mobile)
                .params("code", code)
                .params("opwd", opwd)
                .params("opwd1", opwd1)
                .execute(new JsonCallBack<HttpResult>(this) {
                    @Override
                    protected void onNext(HttpResult result) {
                        ToastUtils.show(result.getMsg());
                        mView.resetPsSuccess();
                    }
                });
    }
}
