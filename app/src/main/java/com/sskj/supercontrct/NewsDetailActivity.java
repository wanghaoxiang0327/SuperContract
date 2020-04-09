package com.sskj.supercontrct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
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
    @BindView(R.id.content_layout)
    LinearLayout linearLayout;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;

    private AgentWeb agentWeb;


    private int type;

    private NewsBean newsBean;

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
        newsBean = (NewsBean) getIntent().getSerializableExtra("newsBean");
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initData() {
        if (type == 1) {
            mPresenter.getNoticeDetail(newsBean.getId());
            linearLayout.setVisibility(View.GONE);
            topLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            agentWeb = AgentWeb.with(this)
                    .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(newsBean.getContext());
            topLayout.setVisibility(View.GONE);
        }
    }

    public static void start(Context context, NewsBean newsBean, int type) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("newsBean", newsBean);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    public void setNoticeDetail(NewsBean data) {
        setText(titleTv, data.getTitle());
        setText(timeTv, data.getDate());
        RichText.fromHtml(data.getContent()).into(contentTv);
    }
}
