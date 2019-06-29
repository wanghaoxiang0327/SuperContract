package com.sskj.market;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.FlowableBody;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.common.http.Page;
import com.sskj.market.HoldFragment;
import com.sskj.market.data.HoldBean;

import java.util.List;

import io.reactivex.Flowable;


/**
 * @author Hey
 * Create at  2019/06/26
 */
public class HoldPresenter extends BasePresenter<HoldFragment> {


//    public void getOrder(int state, int page, int size) {
//        OkGo.<HttpResult<Page<HoldBean>>>post(BaseHttpConfig.BASE_URL + HttpConfig.MYORDER)
//                .params("state", state)
//                .params("p", page)
//                .params("size", size)
//                .execute(new JsonCallBack<HttpResult<Page<HoldBean>>>(this) {
//                    @Override
//                    protected void onNext(HttpResult<Page<HoldBean>> result) {
//                        mView.setData(result.getData());
//                    }
//                });
//
//    }

    public Flowable<List<HoldBean>> getOrder(int state, int page, int size) {
        return OkGo.<HttpResult<Page<HoldBean>>>post(BaseHttpConfig.BASE_URL + HttpConfig.MYORDER)
                .params("state", state)
                .params("p", page)
                .params("size", size)
                .converter(new JsonConvert<HttpResult<Page<HoldBean>>>() {
                })
                .adapt(new FlowableBody<>())
                .map(pageHttpResult -> {
                    return pageHttpResult.getData().getRes();
                });

    }


}
