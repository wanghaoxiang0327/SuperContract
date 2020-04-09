package com.sskj.market;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.market.data.CoinBean;

import java.util.List;

import io.reactivex.Flowable;


/**
 * @author Hey
 * Create at  2019/05/29
 */
class MarketListPresenter extends BasePresenter<MarketListFragment> {

    public Flowable<List<CoinBean>> getMarketList(String code) {
        return OkGo.<HttpResult<List<CoinBean>>>get(HttpConfig.BASE_URL + HttpConfig.GET_PRODUCT)
                .params("code", code)
                .converter(new JsonConvert<HttpResult<List<CoinBean>>>() {
                })
                .adapt(new FlowableBody<>())
                .map(listHttpResult -> listHttpResult.getData());
    }




}
