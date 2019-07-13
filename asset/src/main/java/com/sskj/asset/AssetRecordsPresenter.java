package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.common.App;
import com.sskj.common.data.AssetType;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.common.http.Page;
import com.sskj.common.language.LocalManageUtil;

import java.util.List;

import io.reactivex.Flowable;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class AssetRecordsPresenter extends BasePresenter<AssetRecordsActivity> {


    /**
     * 资产明细
     */
    public Flowable<List<AssetRecordsBean>> getAssetDetail(String pid, String type, int p, int size) {
        return OkGo.<HttpResult<Page<AssetRecordsBean>>>post(BaseHttpConfig.BASE_URL + HttpConfig.ASSET_RECORDS)
                .params("pid", pid)
                .params("type", type)
                .params("p", p)
                .params("size", size)
                .params("lang", LocalManageUtil.getLanguage(App.INSTANCE))
                .converter(new JsonConvert<HttpResult<Page<AssetRecordsBean>>>() {
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

    public void getAssetType(boolean showDialog, String lang) {
        OkGo.<HttpResult<List<AssetType>>>get(BaseHttpConfig.BASE_URL + HttpConfig.ASSET_TYPE)
                .params("lang", lang)
                .execute(new JsonCallBack<HttpResult<List<AssetType>>>() {
                    @Override
                    protected void onNext(HttpResult<List<AssetType>> result) {
                        if (!showDialog) {
                            mView.setTypeList(result.getData());
                        } else {
                            mView.showTypeDialog(result.getData());
                        }
                    }
                });

    }


}
