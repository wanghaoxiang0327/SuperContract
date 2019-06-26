package com.sskj.supercontrct;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.supercontrct.data.NewsBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 0行业资讯 1 平台公告
 * Create at  2019/06/26
 */
public class InformationFragment extends BaseFragment<InformationPresenter> {


    @BindView(R.id.news_list)
    RecyclerView newsList;

    private int type;

    int page = 1;
    int size = 10;

    BaseAdapter<NewsBean> newsAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.app_fragment_information;
    }

    @Override
    public InformationPresenter getPresenter() {
        return new InformationPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    public void initView() {
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new BaseAdapter<NewsBean>(R.layout.app_item_news, null, newsList) {
            @Override
            public void bind(ViewHolder holder, NewsBean item) {

            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    /**
     * @param type 0行业资讯 1 平台公告
     * @return
     */
    public static InformationFragment newInstance(int type) {
        InformationFragment fragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


}
