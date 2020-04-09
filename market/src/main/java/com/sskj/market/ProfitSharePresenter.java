package com.sskj.market;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.http.JsonConvert;
import com.sskj.market.ProfitShareActivity;
import com.sskj.market.data.OrderDetail;
import com.sskj.market.data.TradeCoin;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author Hey
 * Create at  2019/07/01
 */
public class ProfitSharePresenter extends BasePresenter<ProfitShareActivity> {

    public void getOrderDetail(String id) {
         OkGo.<HttpResult<OrderDetail>>post(BaseHttpConfig.BASE_URL + HttpConfig.ORDER_DETAIL)
                .params("id", id)
               .execute(new JsonCallBack<HttpResult<OrderDetail>>(this){
                   @Override
                   protected void onNext(HttpResult<OrderDetail> result) {

                       mView.setOrderDetail(result.getData());
                   }
               });
    }
}
