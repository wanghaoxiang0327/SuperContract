package com.sskj.mine;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.user.data.UserBean;
import com.sskj.mine.VerifySettingActivity;

import io.reactivex.schedulers.Schedulers;


/**
 * @author Hey
 * Create at  2019/06/25
 */
public class VerifySettingPresenter extends BasePresenter<VerifySettingActivity> {


    /**
     * 设置短信验证开关
     *
     * @param state 0 关闭 1 开启
     */
    public void setSmsState(int state,String code) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.SET_SMS_STATE)
                .params("state", state)
                .params("code", code)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.setStateSuccess(state == 1);
                    }
                });
    }


    /**
     * 设置谷歌验证开关
     *
     * @param state 0 关闭 1 开启
     * @param code
     */
    public void setGoogleState(int state, String code) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.SET_GOOGLE_STATE)
                .params("act", state)
                .params("dyGoodleCommand", code)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.setStateSuccess(state == 1);
                    }
                });
    }
}
