package com.sskj.asset;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.sskj.asset.data.TransferInfo;
import com.sskj.asset.data.WithdrawInfo;
import com.sskj.common.base.BasePresenter;
import com.sskj.asset.WithdrawActivity;
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
public class WithdrawPresenter extends BasePresenter<WithdrawActivity> {



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
     * 提币信息
     */
    public void getWithdrawInfo(String pid) {
        OkGo.<HttpResult<WithdrawInfo>>post(BaseHttpConfig.BASE_URL + HttpConfig.WITHDRAW_INFO)
                .params("type", pid)
                .execute(new JsonCallBack<HttpResult<WithdrawInfo>>(this){
                    @Override
                    protected void onNext(HttpResult<WithdrawInfo> result) {
                        mView.setWithDrawInfo(result.getData());
                    }
                });

    }


    public void withdraw(String url,String pid,String num,String tpwd,String code,String googleCode) {
        OkGo.<HttpResult<Object>>post(BaseHttpConfig.BASE_URL + HttpConfig.WITHDRAW)
                .params("type", pid)
                .params("qianbao_url", url)
                .params("num", num)
                .params("tpwd", tpwd)
                .params("code", code)
                .params("googleCode", googleCode)
                .execute(new JsonCallBack<HttpResult<Object>>(this){
                    @Override
                    protected void onNext(HttpResult<Object> result) {
                        ToastUtils.show(result.getMsg());
                        mView.withdrawSuccess();
                    }
                });

    }


}
