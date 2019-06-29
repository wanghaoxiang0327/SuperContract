package com.sskj.mine;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class ResetPayPasswordPresenter extends BasePresenter<ResetPayPasswordActivity> {
    /**
     * 重置资金密码
     *
     * @param state 0 关闭 1 开启
     */
    public void resetLoginPs(String oldpwd,String opwd,String opwd1,String smsCode,String googleCode) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.RESET_PAY_PS)
                .params("otpwd", oldpwd)
                .params("tpwd", opwd)
                .params("tpwd1", opwd1)
                .params("code", smsCode)
                .params("googleCode", googleCode)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.resetPsSuccess();
                    }
                });
    }
}
