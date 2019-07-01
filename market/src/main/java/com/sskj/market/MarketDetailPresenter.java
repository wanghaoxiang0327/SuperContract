package com.sskj.market;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpObserver;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.common.http.RxUtils;
import com.sskj.common.data.CoinAsset;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.TradeCoin;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author Hey
 * Create at  2019/05/22
 */
public class MarketDetailPresenter extends BasePresenter<MarketDetailActivity> {


    public void getTradeCoinList(boolean showDialog) {
        getTradeCoin().compose(RxUtils.transform())
                .subscribe(new HttpObserver<HttpResult<List<TradeCoin>>>(this) {
                    @Override
                    protected void onSuccess(HttpResult<List<TradeCoin>> result) {
                        mView.setCoinList(result.getData(),showDialog);
                    }
                });
    }


    public Observable<HttpResult<List<CoinAsset>>> getCoinAsset() {
        return OkGo.<HttpResult<List<CoinAsset>>>get(BaseHttpConfig.BASE_URL + HttpConfig.COINASSET)
                .converter(new JsonConvert<HttpResult<List<CoinAsset>>>() {
                })
                .adapt(new ObservableBody<HttpResult<List<CoinAsset>>>());

    }


    public Observable<HttpResult<List<TradeCoin>>> getTradeCoin() {
        return OkGo.<HttpResult<List<TradeCoin>>>get(BaseHttpConfig.BASE_URL + HttpConfig.TRADECOIN)
                .converter(new JsonConvert<HttpResult<List<TradeCoin>>>() {
                })
                .adapt(new ObservableBody<>());
    }


    /**
     * 下单弹框信息获取
     *
     * @param up
     */
    public void getTradeInfo(boolean up) {
        getCoinAsset().zipWith(getTradeCoin(), (listHttpResult, listHttpResult2) -> {
            TradeInfo tradeInfo = new TradeInfo();
            tradeInfo.setCoinAssets(listHttpResult.getData());
            tradeInfo.setTradeCoins(listHttpResult2.getData());
            return tradeInfo;
        }).compose(RxUtils.transform())
                .subscribe(new HttpObserver<TradeInfo>(this) {
                    @Override
                    protected void onSuccess(TradeInfo tradeInfo) {
                        mView.setCoinInfo(tradeInfo, up);
                    }
                });
    }


    /**
     * 创建订单
     *
     * @param type
     * @param ptype
     * @param unit_num
     * @param num
     * @param pid
     * @param aim_point
     */
    public void createOrder(int type, String ptype, Double unit_num, int num, String pid, Double aim_point) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.CREATE_ORDER)
                .params("type", type)
                .params("ptype", ptype)
                .params("unit_num", unit_num)
                .params("num", num)
                .params("pid", pid)
                .params("aim_point", aim_point)
                .execute(new JsonCallBack<HttpResult<Object>>(this) {
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        mView.createOrderSuccess();
                    }
                });

    }

    public void getMarketList() {
        OkGo.<HttpResult<List<CoinBean>>>get(HttpConfig.BASE_URL + HttpConfig.GET_PRODUCT)
                .execute(new JsonCallBack<HttpResult<List<CoinBean>>>(this) {
                    @Override
                    protected void onNext(HttpResult<List<CoinBean>> result) {
                        mView.setCoin(result.getData());
                    }
                });

    }


}
