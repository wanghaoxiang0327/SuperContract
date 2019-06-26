package com.sskj.supercontrct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create at  2019/06/26
 */
public class NewsFragment extends BaseFragment<NewsPresenter> {


    @BindView(R2.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R2.id.news_content)
    FrameLayout newsContent;

    ArrayList<CustomTabEntity> tabs=new ArrayList<>();
    ArrayList<Fragment> fragments=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.app_fragment_news;
    }

    @Override
    public NewsPresenter getPresenter() {
        return new NewsPresenter();
    }

    @Override
    public void initView() {
        tabs.add(new TabItem("行业资讯"));
        tabs.add(new TabItem("平台公告"));
        fragments.add(InformationFragment.newInstance(0));
        fragments.add(InformationFragment.newInstance(1));
        tabLayout.setTabData(tabs,getChildFragmentManager(),R.id.news_content,fragments);
    }


    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


}
