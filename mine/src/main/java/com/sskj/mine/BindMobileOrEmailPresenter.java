package com.sskj.mine;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.Page;
import com.sskj.mine.BindMobileOrEmailActivity;


/**
 * @author Hey
 * Create at  2019/06/25
 */
public class BindMobileOrEmailPresenter extends BasePresenter<BindMobileOrEmailActivity> {


    public void bindEmail(String email, String code) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.BIND_EMAIL)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        mView.bindSuccess(result.getData());
                    }
                });
    }

    public void bindMobile(String mobile, String code) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.BIND_MOBILE)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        mView.bindSuccess(result.getData());
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
