package com.sskj.supercontrct;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.data.VersionBean;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;

public class MainPresenter extends BasePresenter<MainActivity> {


    public void checkVersion(String versionName){
        OkGo.<HttpResult<VersionBean>>post(BaseHttpConfig.BASE_URL+ HttpConfig.CHECK_VERSION)
                .params("verison",versionName)
                .params("type",1)
                .execute(new JsonCallBack<HttpResult<VersionBean>>(){
                    @Override
                    protected void onNext(HttpResult<VersionBean> result) {
                        mView.checkVersion(result);
                    }
                });
    }
}
