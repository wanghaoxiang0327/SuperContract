package com.sskj.supercontrct;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.Page;
import com.sskj.supercontrct.NewsDetailActivity;
import com.sskj.supercontrct.data.NewsBean;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class NewsDetailPresenter extends BasePresenter<NewsDetailActivity> {


    public void getNoticeDetail(String id) {
        OkGo.<HttpResult<NewsBean>>get(HttpConfig.BASE_URL + HttpConfig.NOTICE_DETAIL)
                .params("id",id)
                .execute(new JsonCallBack<HttpResult<NewsBean>>(this) {
                    @Override
                    protected void onNext(HttpResult<NewsBean> result) {
                        mView.setNoticeDetail(result.getData());
                    }
                });

    }
}
