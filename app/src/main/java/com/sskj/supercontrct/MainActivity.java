package com.sskj.supercontrct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.base.NormalActivity;
import com.sskj.common.router.RoutePath;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Hey
 */
@Route(path = RoutePath.MAIN)
public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.main_tab_layout)
    TabLayout mainTabLayout;

    ArrayList<CustomTabEntity> mainTabs = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainTabs.add(new TabItem("首页", R.mipmap.tab_home, R.mipmap.tab_home_p));
        mainTabs.add(new TabItem("资讯", R.mipmap.tab_news, R.mipmap.tab_news_p));
        mainTabs.add(new TabItem("资产", R.mipmap.tab_asset, R.mipmap.tab_asset_p));
        mainTabs.add(new TabItem("我的", R.mipmap.tab_mine, R.mipmap.tab_mine_p));
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        mainTabLayout.setTabData(mainTabs,getSupportFragmentManager(),R.id.main_content,fragments);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }
}
