package com.sskj.supercontrct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/26
 */
public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> {


    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    public int getLayoutId() {
        return R.layout.app_activity_news_detail;
    }

    @Override
    public NewsDetailPresenter getPresenter() {
        return new NewsDetailPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        context.startActivity(intent);
    }


}
