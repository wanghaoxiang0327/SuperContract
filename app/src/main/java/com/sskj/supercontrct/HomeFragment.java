package com.sskj.supercontrct;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.sskj.common.base.BaseFragment;
import com.sskj.common.glide.GlideImageLoader;
import com.sskj.common.glide.ZoomOutPageTransformer;
import com.sskj.market.MarketListFragment;
import com.sskj.market.dialog.CreateOrderDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create at  2019/06/21
 */
public class HomeFragment extends BaseFragment<HomePresenter> {

    @BindView(R.id.change_language)
    TextView changeLanguage;
    @BindView(R.id.bannerView)
    Banner bannerView;

    List<String> bannerImages = new ArrayList<>();
    @BindView(R.id.home_content)
    NestedScrollView homeContent;
    @BindView(R.id.tvNotice)
    TextSwitcher tvNotice;
    @BindView(R.id.topCoinRecyclerView)
    RecyclerView topCoinRecyclerView;
    @BindView(R.id.home_coin_list)
    FrameLayout homeCoinList;

    @Override
    public int getLayoutId() {
        return R.layout.app_fragment_home;
    }

    @Override
    public HomePresenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    public void initView() {
        bannerView.setImageLoader(new GlideImageLoader());
        bannerImages.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561120133254&di=9472a6c875e07f2d3eba6fdc09ef14d4&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170517%2F5334d6802d164ead9acae878e9b826bf_th.jpg");
        bannerImages.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561120134184&di=3e171ec7d493110ba0734bbcdd3909bb&imgtype=0&src=http%3A%2F%2Fimg.juimg.com%2Ftuku%2Fyulantu%2F110915%2F15-1109150P62810.jpg");
        bannerImages.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561120134184&di=001ddecdfdd5beef2cfd4fd6cf1e3cc9&imgtype=0&src=http%3A%2F%2Fpic21.nipic.com%2F20120513%2F2786001_164627479000_2.jpg");
        bannerView.setImages(bannerImages);
        bannerView.setOffscreenPageLimit(1);
        bannerView.setPageTransformer(false, new ZoomOutPageTransformer());
        bannerView.start();
        FragmentTransaction ft=getChildFragmentManager().beginTransaction();
        ft.replace(R.id.home_coin_list, MarketListFragment.newInstance());
        ft.commitAllowingStateLoss();
    }


    @Override
    public void initData() {
        tvNotice.setOnClickListener(view -> {
            new CreateOrderDialog(getContext()).show();
        });
    }

    @Override
    public void loadData() {

    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        bannerView.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        bannerView.stopAutoPlay();
    }

}
