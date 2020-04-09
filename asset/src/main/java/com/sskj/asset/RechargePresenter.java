package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.common.base.BasePresenter;
import com.sskj.asset.RechargeActivity;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.common.http.Page;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class RechargePresenter extends BasePresenter<RechargeActivity> {


    /**
     * 币种分类
     *
     * @return
     */
    public void getCoinAsset(boolean showDialog) {
        OkGo.<HttpResult<List<CoinAsset>>>get(BaseHttpConfig.BASE_URL + HttpConfig.COINASSET)
                .execute(new JsonCallBack<HttpResult<List<CoinAsset>>>() {
                    @Override
                    protected void onNext(HttpResult<List<CoinAsset>> result) {
                        if (!showDialog) {
                            mView.setCoinList(result.getData());
                        } else {
                            mView.showCoinDialog(result.getData());
                        }
                    }
                });
    }




    /**
     * 充值信息
     */
    public void getRechargeInfo(String pid) {
         OkGo.<HttpResult<Map<String,String>>>post(BaseHttpConfig.BASE_URL + HttpConfig.RECHARGE_INFO)
                .params("pid", pid)
                .execute(new JsonCallBack<HttpResult<Map<String,String>>>(this){
                    @Override
                    protected void onNext(HttpResult<Map<String, String>> result) {
                        mView.setRechargeInfo(result.getData());
                    }
                });

    }


}
