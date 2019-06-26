package com.sskj.mine;

import com.sskj.common.base.BaseActivity;
import com.sskj.mine.SettingPasswordPresenter;
import android.content.Context;
import android.content.Intent;
import com.sskj.mine.R;
/**
 * 设置支付密码
 * @author Hey
 * Create at  2019/06/25
 */
public class SettingPasswordActivity extends BaseActivity<SettingPasswordPresenter> {

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_setting_password;
    }

    @Override
    public SettingPasswordPresenter getPresenter() {
        return new SettingPasswordPresenter();
    }

    @Override
    public void initView() {
    
    }

    @Override
    public void initData() {

    }

    public static void start(Context context){
        Intent intent=new Intent(context,SettingPasswordActivity.class);
        context.startActivity(intent);
    }
   
}
