package com.sskj.market;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.Page;
import com.sskj.market.HoldFragment;
import com.sskj.market.data.HoldBean;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class HoldPresenter extends BasePresenter<HoldFragment> {


    public void getOrder(int state,int page,int size){
        OkGo.<HttpResult<Page<HoldBean>>>post(BaseHttpConfig.BASE_URL+ HttpConfig.MYORDER)
                .params("state",state)
                .params("p",page)
                .params("size",size)
                .execute(new JsonCallBack<HttpResult<Page<HoldBean>>>(this) {
                    @Override
                    protected void onNext(HttpResult<Page<HoldBean>> result) {
                        mView.setData(result.getData());
                    }
                });

    }

}
