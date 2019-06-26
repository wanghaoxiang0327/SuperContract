package com.sskj.mine;

import com.sskj.common.base.BaseActivity;
import com.sskj.mine.LanguagePresenter;

import android.content.Context;
import android.content.Intent;

import com.sskj.mine.R;

/**
 * @author Hey
 * Create at  2019/06/24
 */
public class LanguageActivity extends BaseActivity<LanguagePresenter> {


    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_language;
    }

    @Override
    public LanguagePresenter getPresenter() {
        return new LanguagePresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LanguageActivity.class);
        context.startActivity(intent);
    }

}
