package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.asset.InsertAddressActivity;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class InsertAddressPresenter extends BasePresenter<InsertAddressActivity> {


    public void insertAddress(String address, String notes, String mobile, String type) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.ADDRESS_MANAGE)
                .params("address", address)
                .params("notes", notes)
                .params("mobile", mobile)
                .params("type", type)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        mView.insertSuccess();
                    }
                });

    }
}
