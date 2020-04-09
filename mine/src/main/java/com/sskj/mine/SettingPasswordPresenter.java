package com.sskj.mine;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.mine.SettingPasswordActivity;


/**
 * @author Hey
 * Create at  2019/06/25
 */
public class SettingPasswordPresenter extends BasePresenter<SettingPasswordActivity> {

    /**
     * 设置资金密码
     *
     * @param
     */
    public void resetLoginPs(String opwd,String opwd1,String smsCode,String googleCode) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.RESET_PAY_PS)
                .params("tpwd", opwd)
                .params("tpwd1", opwd1)
                .params("code", smsCode)
                .params("googleCode", googleCode)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.setPsSuccess();
                    }
                });
    }
}
