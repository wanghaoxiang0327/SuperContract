package com.sskj.supercontrct;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.market.data.CoinBean;
import com.sskj.supercontrct.HomeFragment;

import java.util.List;


/**
 * @author Hey
 * Create at  2019/06/21
 */
public class HomePresenter extends BasePresenter<HomeFragment> {
    public void getMarketList() {
        OkGo.<HttpResult<List<CoinBean>>>get(HttpConfig.BASE_URL + HttpConfig.GET_PRODUCT)
                .execute(new JsonCallBack<HttpResult<List<CoinBean>>>(this) {
                    @Override
                    protected void onNext(HttpResult<List<CoinBean>> result) {
                        mView.setData(result.getData());
                    }
                });

    }
}
