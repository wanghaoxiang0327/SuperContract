package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.asset.data.TransferRecodsBean;
import com.sskj.asset.data.WithdrawRecordsBean;
import com.sskj.common.base.BasePresenter;
import com.sskj.asset.WithdrawRecordsActivity;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.common.http.Page;

import java.util.List;

import io.reactivex.Flowable;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class WithdrawRecordsPresenter extends BasePresenter<WithdrawRecordsActivity> {


    /**
     * 提币记录
     * @param type  cash提币 recharge充币
     * @param pid 资产币种pid
     * @param p
     * @param size
     * @return
     */
    public Flowable<List<WithdrawRecordsBean>> getWithdrawRecords(String type, String pid, int p, int size) {
        return OkGo.<HttpResult<Page<WithdrawRecordsBean>>>post(BaseHttpConfig.BASE_URL + HttpConfig.WITHDRAW_RECORDS)
                .params("type", type)
                .params("pid", pid)
                .params("p", p)
                .params("size", size)
                .converter(new JsonConvert<HttpResult<Page<WithdrawRecordsBean>>>() {
                })
                .adapt(new FlowableBody<>())
                .map(pageHttpResult -> {
                    return pageHttpResult.getData().getRes();
                });

    }


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
}
