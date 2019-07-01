package com.sskj.common;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.WebActivity;
import com.sskj.common.data.WebData;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;


/**
 * @author Hey
 * Create at  2019/07/01
 */
public class WebPresenter extends BasePresenter<WebActivity> {


    public void getOrderDetail(String language) {
        OkGo.<HttpResult<WebData>>post(BaseHttpConfig.BASE_URL + HttpConfig.WEB_AGGREE)
                .params("lang", language)
                .execute(new JsonCallBack<HttpResult<WebData>>(this){
                    @Override
                    protected void onNext(HttpResult<WebData> result) {

                        mView.setData(result.getData());
                    }
                });
    }

}
