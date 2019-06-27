package com.sskj.market;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.Page;
import com.sskj.market.ProfitOrderFragment;
import com.sskj.market.data.OrderBean;

import java.util.List;


/**
 * @author Hey
 * Create at  2019/06/27
 */
public class ProfitOrderPresenter extends BasePresenter<ProfitOrderFragment> {
    public void getProfitOrder() {
        OkGo.<HttpResult<Page<OrderBean>>>post(BaseHttpConfig.BASE_URL + HttpConfig.PROFIT_ORDER)
                .execute(new JsonCallBack<HttpResult<Page<OrderBean>>>(this) {
                    @Override
                    protected void onNext(HttpResult<Page<OrderBean>> result) {
                        mView.setData(result.getData());
                    }
                });

    }
}
