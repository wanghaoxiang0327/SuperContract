package com.sskj.mine;

import com.lzy.okgo.OkGo;
import com.sskj.common.App;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.common.language.LocalManageUtil;
import com.sskj.mine.DirectorActivity;
import com.sskj.mine.data.CommissionBean;
import com.sskj.mine.data.DividendBean;


/**
 * @author Hey
 * Create at  2019/06/25
 */
public class DirectorPresenter extends BasePresenter<DirectorActivity> {


    /**
     * 董事分红
     * @param page
     * @param size
     */
    public void getCommsion(int page,int size){
        OkGo.<HttpResult<DividendBean>>get(BaseHttpConfig.BASE_URL+ HttpConfig.DIRECTOR_PROFIT)
                .params("p",page)
                .params("size",size)
                .params("lang", LocalManageUtil.getLanguage(App.INSTANCE))
                .execute(new JsonCallBack<HttpResult<DividendBean>>(this) {
                    @Override
                    protected void onNext(HttpResult<DividendBean> result) {
                        mView.setData(result.getData());
                    }
                });
    }
}
