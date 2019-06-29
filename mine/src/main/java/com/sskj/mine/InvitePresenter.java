package com.sskj.mine;

import com.lzy.okgo.OkGo;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.JsonCallBack;
import com.sskj.mine.InviteActivity;
import com.sskj.mine.data.ShareInfo;


/**
 * @author Hey
 * Create at  2019/06/25
 */
public class InvitePresenter extends BasePresenter<InviteActivity> {


    public void getShareInfo(){
        OkGo.<HttpResult<ShareInfo>>get(BaseHttpConfig.BASE_URL+ HttpConfig.SHARE_INFO)
                .execute(new JsonCallBack<HttpResult<ShareInfo>>(this) {
                    @Override
                    protected void onNext(HttpResult<ShareInfo> result) {
                            mView.setShareInfo(result.getData());
                    }
                });
    }
}
