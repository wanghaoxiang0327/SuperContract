package com.sskj.mine;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.mine.BindGoogleVerifyActivity;
import com.sskj.mine.data.GoogleInfo;


/**
 * @author Hey
 * Create at  2019/06/25
 */
public class BindGoogleVerifyPresenter extends BasePresenter<BindGoogleVerifyActivity> {


    /**
     * 设置短信验证开关
     *
     */
    public void getGoogleInfo() {
        OkGo.<HttpResult<GoogleInfo>>post(BaseHttpConfig.BASE_URL + HttpConfig.GET_GOOGLE_INFO)
                .execute(new JsonCallBack<HttpResult<GoogleInfo>>(this) {
                    @Override
                    protected void onNext(HttpResult<GoogleInfo> result) {
                        mView.setGoogleInfo(result.getData());
                    }
                });
    }
    /**
     * 设置短信验证开关
     *
     */
    public void bindGoogle(String code) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.BIND_GOOGLE)
                .params("dyGoodleCommand",code)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.bindGoogleSuccess();
                    }
                });
    }
}
