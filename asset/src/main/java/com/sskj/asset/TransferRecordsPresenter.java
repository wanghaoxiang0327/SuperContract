package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.asset.data.TransferRecodsBean;
import com.sskj.common.base.BasePresenter;
import com.sskj.asset.TransferRecordsActivity;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonConvert;
import com.sskj.common.http.Page;

import java.util.List;

import io.reactivex.Flowable;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class TransferRecordsPresenter extends BasePresenter<TransferRecordsActivity> {


    /**
     * 转账记录
     */
    public Flowable<List<TransferRecodsBean>> getTransferRecord(String pid, int p, int size) {
        return OkGo.<HttpResult<Page<TransferRecodsBean>>>post(BaseHttpConfig.BASE_URL + HttpConfig.TRANSFER_RECORD)
                .params("pid", pid)
                .params("p", p)
                .params("size", size)
                .converter(new JsonConvert<HttpResult<Page<TransferRecodsBean>>>() {
                })
                .adapt(new FlowableBody<>())
                .map(pageHttpResult -> {
                    return pageHttpResult.getData().getRes();
                });

    }
}
