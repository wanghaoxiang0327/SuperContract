package com.sskj.asset;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.asset.data.TransferInfo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;

import java.util.List;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class TransferPresenter extends BasePresenter<TransferActivity> {

    /**
     * 币种分类
     *
     * @return
     */
    public void getCoinAsset(boolean showDialog) {
        OkGo.<HttpResult<List<CoinAsset>>>get(BaseHttpConfig.BASE_URL + HttpConfig.COINASSET)
                .execute(new JsonCallBack<HttpResult<List<CoinAsset>>>(this) {
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
     * 转账信息
     */
    public void getTransferInfo(String pid) {
        OkGo.<HttpResult<TransferInfo>>post(BaseHttpConfig.BASE_URL + HttpConfig.TRANSFER_INFO)
                .params("type", pid)
                .execute(new JsonCallBack<HttpResult<TransferInfo>>(this){
                    @Override
                    protected void onNext(HttpResult<TransferInfo> result) {
                        mView.setTransferInfo(result.getData());
                    }
                });

    }

    public void transfer(String account,String pid,String num,String tpwd,String code,String googleCode) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.TRANSFER)
                .params("pid", pid)
                .params("oaccount", account)
                .params("num", num)
                .params("tpwd", tpwd)
                .params("code", code)
                .params("googleCode", googleCode)
                .execute(new JsonCallBack<HttpResult<Object>>(this){
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.transferSuccess();
                    }
                });

    }

}
