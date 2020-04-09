package com.sskj.asset;

import com.lzy.okgo.OkGo;
import com.sskj.asset.data.AssetData;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class AssetPresenter extends BasePresenter<AssetFragment> {

    /**
     * 资产列表
     *
     */
    public void getAsset() {
        OkGo.<HttpResult<AssetData>>post(BaseHttpConfig.BASE_URL + HttpConfig.ASSETLIST)
                .params("p", 1)
                .params("size", 20)
                .execute(new JsonCallBack<HttpResult<AssetData>>(this) {
                    @Override
                    protected void onNext(HttpResult<AssetData> result) {
                        mView.setAsset(result.getData());
                    }
                });
    }





//    public Flowable<List<AssetData>> getAssetList(int page, int size) {
//        return OkGo.<HttpResult<Page<AssetData>>>post(BaseHttpConfig.BASE_URL + HttpConfig.ASSETLIST)
//                .params("p", page)
//                .params("size", size)
//                .converter(new JsonConvert<HttpResult<Page<AssetData>>>() {
//                })
//                .adapt(new FlowableBody<>())
//                .map(pageHttpResult -> {
//                    return pageHttpResult.getData().getRes();
//                });
//
//    }

}
