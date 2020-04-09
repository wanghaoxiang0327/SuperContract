package com.sskj.supercontrct;

import com.lzy.okgo.OkGo;
import com.sskj.common.App;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.Page;
import com.sskj.common.language.LocalManageUtil;
import com.sskj.market.data.CoinBean;
import com.sskj.supercontrct.HomeFragment;
import com.sskj.supercontrct.data.BannerBean;
import com.sskj.supercontrct.data.NewsBean;

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


    public void getBanner(String lang) {
        OkGo.<HttpResult<List<BannerBean>>>get(HttpConfig.BASE_URL + HttpConfig.BANNER)
                .params("type", 0)
                .params("lang", lang)
                .execute(new JsonCallBack<HttpResult<List<BannerBean>>>(this) {
                    @Override
                    protected void onNext(HttpResult<List<BannerBean>> result) {
                        mView.setBanner(result.getData());
                    }
                });

    }

    public void getNotice() {
        String url = "http://47.110.35.218:90/api/client_hand/Login";
        OkGo.<HttpResult<Page<NewsBean>>>post(url)
                .params("loginName", "chenxiaowei")
                .params("loginPwd", "66666666")
                .execute(new JsonCallBack<HttpResult<Page<NewsBean>>>(this) {
                    @Override
                    protected void onNext(HttpResult<Page<NewsBean>> result) {
                        mView.setNotice(result.getData());
                    }
                });

    }
}
