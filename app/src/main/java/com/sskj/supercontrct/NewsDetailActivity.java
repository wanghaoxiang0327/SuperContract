package com.sskj.supercontrct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;
import com.sskj.common.http.Page;
import com.sskj.supercontrct.data.NewsBean;
import com.zzhoujay.richtext.RichText;

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
    private String id;

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
        id=getIntent().getStringExtra("id");
    }

    @Override
    public void initData() {
        mPresenter.getNoticeDetail(id);
    }

    public static void start(Context context,String id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }


    public void setNoticeDetail(NewsBean data) {
        setText(titleTv, data.getTitle());
        setText(timeTv, data.getDate());
        RichText.fromHtml(data.getContent()).into(contentTv);
    }
}
