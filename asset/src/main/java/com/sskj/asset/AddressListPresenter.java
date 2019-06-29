package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.sskj.asset.data.AddressBean;
import com.sskj.asset.data.WithdrawInfo;
import com.sskj.common.base.BasePresenter;
import com.sskj.asset.AddressListActivity;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class AddressListPresenter extends BasePresenter<AddressListActivity> {

    public void getAddressList() {

        OkGo.<HttpResult<AddressBean>>get(BaseHttpConfig.BASE_URL + HttpConfig.ADDRESS_LIST)
                .execute(new JsonCallBack<HttpResult<AddressBean>>(this) {
                    @Override
                    protected void onNext(HttpResult<AddressBean> result) {
                        mView.setData(result.getData());
                    }
                });
    }


    public void deleteAddress(String id) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.ADDRESS_MANAGE)
                .params("id", id)
                .params("act", "del")
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        mView.deleteSuccess(result.getData());
                    }
                });

    }
}
