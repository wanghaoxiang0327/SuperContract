package com.sskj.supercontrct;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.glide.GlideImageLoader;
import com.sskj.common.glide.ZoomOutPageTransformer;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.Page;
import com.sskj.common.language.LanguageSPUtil;
import com.sskj.common.router.RoutePath;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.market.MarketListFragment;
import com.sskj.market.data.CoinBean;
import com.sskj.mine.LanguageActivity;
import com.sskj.supercontrct.data.BannerBean;
import com.sskj.supercontrct.data.NewsBean;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Hey
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


    private BaseAdapter<CoinBean> topAdapter;

    List<CoinBean> topList = new ArrayList<>();

    private MarketListFragment marketListFragment;
    private Disposable noticeDisposable;


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
        getLifecycle().addObserver(RxBus.getDefault(this, true));
        bannerView.setImageLoader(new GlideImageLoader());
        bannerView.setOffscreenPageLimit(1);
        bannerView.setPageTransformer(false, new ZoomOutPageTransformer());
        marketListFragment = MarketListFragment.newInstance();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.home_coin_list, marketListFragment);
        ft.commitAllowingStateLoss();
        topCoinRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        topCoinRecyclerView.getItemAnimator().setChangeDuration(0);
        topAdapter = new BaseAdapter<CoinBean>(R.layout.market_item_top_coin, null, topCoinRecyclerView) {
            @Override
            public void bind(ViewHolder holder, CoinBean item) {
                holder.setText(R.id.coin_name, item.getCode())
                        .setText(R.id.coin_price, NumberUtils.keepDown(item.getPrice() , DigitUtils.getDigit(item.getCode())))
                        .setText(R.id.coin_cny_price, "≈" + item.getCnyPrice() + " CNY")
                        .setText(R.id.coin_change_rate, item.getChange() > 0 ? "+" + item.getChangeRate() : item.getChangeRate());
                if (item.isUp()) {
                    holder.setTextColor(R.id.coin_price, color(R.color.market_green));
                    holder.setTextColor(R.id.coin_change_rate, color(R.color.market_green));
                } else {
                    holder.setTextColor(R.id.coin_price, color(R.color.market_red));
                    holder.setTextColor(R.id.coin_change_rate, color(R.color.market_red));
                }

                ClickUtil.click(holder.itemView, view -> {
                    ARouter.getInstance()
                            .build(RoutePath.MARKET_DETAIL)
                            .withSerializable("coinBean", item)
                            .navigation();
                });

            }
        };

        tvNotice.setFactory(() -> {
            TextView textView = new TextView(getActivity());
            textView.setTextColor(color(R.color.common_text));
            textView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(20, 0, 0, 0);
            return textView;
        });
    }


    @Override
    public void initData() {

        switch (LanguageSPUtil.getInstance(getContext()).getSelectLanguage()) {
            case 1:
                changeLanguage.setText(getString(R.string.app_homeFragment1));
                break;
            case 2:
                changeLanguage.setText(getString(R.string.app_homeFragment2));
                break;
            case 3:
                changeLanguage.setText("English");
                break;
            case 4:
                changeLanguage.setText("한글");
                break;
            default:
                break;
        }

        tvNotice.setOnClickListener(view -> {

        });
        wrapRefresh(homeContent);
        setEnableLoadMore(false);
        ClickUtil.click(changeLanguage, view -> {
            LanguageActivity.start(getContext());
        });
    }

    @Override
    public void loadData() {
        mPresenter.getMarketList();
        mPresenter.getBanner();
        mPresenter.getNotice();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        loadData();
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCoin(CoinBean coinBean) {
        if (topAdapter != null) {
            for (int i = 0; i < topAdapter.getData().size(); i++) {
                if (topAdapter.getData().get(i).getCode().equals(coinBean.getCode())) {
                    coinBean.setPid(topAdapter.getData().get(i).getPid());
                    topAdapter.getData().set(i, coinBean);
                    topAdapter.notifyItemChanged(i);
                }
            }
        }
    }


    public void setData(List<CoinBean> data) {
        if (marketListFragment != null) {
            marketListFragment.setData(data);
        }
        topList.clear();
        for (CoinBean coinBean : data) {
            if (coinBean.getCode().equals("BTC/USDT") || coinBean.getCode().equals("ETH/USDT") || coinBean.getCode().equals("EOS/USDT")) {
                topList.add(coinBean);
            }
        }
        topAdapter.setNewData(topList);
    }

    public void setBanner(List<BannerBean> data) {
        bannerImages.clear();
        for (BannerBean bean : data) {
            bannerImages.add(BaseHttpConfig.BASE_URL + bean.getPath());
        }
        bannerView.setImages(bannerImages);
        bannerView.start();
    }

    public void setNotice(Page<NewsBean> data) {
        if (data != null) {
            noticeDisposable = Flowable.interval(0, 5, TimeUnit.SECONDS)
                    .onBackpressureDrop()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(i -> {
                        if (tvNotice != null && data.getRes() != null && !data.getRes().isEmpty()) {
                            int position = (int) (i % data.getRes().size());
                            String text = data.getRes().get(position).getTitle();
                            tvNotice.setText(text);
                            ClickUtil.click(tvNotice, view -> {
                                NewsDetailActivity.start(getContext(), data.getRes().get(position),1);
                            });
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (noticeDisposable!=null){
            noticeDisposable.dispose();
        }
    }
}
