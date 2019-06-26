package com.sskj.asset;

import com.sskj.common.base.BaseActivity;
import com.sskj.asset.RechargePresenter;
import android.content.Context;
import android.content.Intent;
import com.sskj.asset.R;
/**
 * 充币
 * @author Hey
 * Create at  2019/06/26
 */
public class RechargeActivity extends BaseActivity<RechargePresenter> {



    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_recharge;
    }

    @Override
    public RechargePresenter getPresenter() {
        return new RechargePresenter();
    }

    @Override
    public void initView() {
    
    }

    @Override
    public void initData() {

    }

    public static void start(Context context){
        Intent intent=new Intent(context,RechargeActivity.class);
        context.startActivity(intent);
    }
   
}
