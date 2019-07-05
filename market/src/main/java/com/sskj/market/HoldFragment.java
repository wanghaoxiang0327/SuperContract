package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.http.Page;
import com.sskj.common.http.RxUtils;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.simple.SimpleObserver;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.HoldBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Create at  2019/06/26
 */
public class HoldFragment extends BaseFragment<HoldPresenter> {


    BaseAdapter<HoldBean> holdAdapter;
    @BindView(R2.id.records_list)
    RecyclerView recordsList;

    private int mPage = 1;
    private int size = 10;

    SmartRefreshHelper<List<HoldBean>> smartRefreshHelper;

    private Disposable disposable;

    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_hold;
    }

    @Override
    public HoldPresenter getPresenter() {
        return new HoldPresenter();
    }

    @Override
    public void initView() {
        getLifecycle().addObserver(RxBus.getDefault(this, true));
        recordsList.setLayoutManager(new LinearLayoutManager(getContext()));
        recordsList.addItemDecoration(new DividerLineItemDecoration(getContext())
                .setDividerColor(color(R.color.common_dark))
                .setPaintWidth(10)
                .setFirstDraw(false)
                .setLastDraw(false)
        );

        holdAdapter = new BaseAdapter<HoldBean>(R.layout.market_item_hold, null, recordsList, false) {
            @Override
            public void bind(ViewHolder holder, HoldBean item) {
                holder.setText(R.id.name_tv, item.getMark())
                        .setText(R.id.buy_type_tv, item.getType() == 1 ? getString(R.string.market_buy_up) : getString(R.string.market_buy_down))
                        .setText(R.id.pay_coin_tv, item.getPtype())
                        .setText(R.id.price_tv, NumberUtils.keepDown(item.getBuyprice(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.total_price_tv, item.getTotal_num())
                        .setText(R.id.point_tv, item.getAim_point())
                        .setText(R.id.stop_win_tv, NumberUtils.keepDown(item.getStopwin(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.stop_loss_tv, NumberUtils.keepDown(item.getStoploss(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.new_pirce_tv, getString(R.string.market_holdFragment3) + NumberUtils.keepDown(item.getActprice(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_E.format(item.getAddtime() * 1000));
                if (item.getType() == 1) {
                    holder.setTextColor(R.id.buy_type_tv, color(R.color.market_green));
                    holder.getView(R.id.new_pirce_tv).setBackgroundResource(R.drawable.market_green_bg_5);
                } else {
                    holder.setTextColor(R.id.buy_type_tv, color(R.color.market_red));
                    holder.getView(R.id.new_pirce_tv).setBackgroundResource(R.drawable.market_red_bg_5);
                }
            }
        };
    }


    @Override
    public void initData() {
        wrapRefresh(recordsList);
        smartRefreshHelper = new SmartRefreshHelper<>(mRefreshLayout);
        smartRefreshHelper.setDataSource(new DataSource<HoldBean>() {
            @Override
            public Flowable<List<HoldBean>> bindData(int page) {
                mPage = page;
                return mPresenter.getOrder(1, page, size);
            }
        });
        smartRefreshHelper.setAdapter(holdAdapter);
        Observable.interval(3, TimeUnit.SECONDS)
                .compose(RxUtils.transform())
                .subscribe(new SimpleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mPresenter.getOrderEx(1, mPage, size);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCoin(CoinBean coinBean) {
        if (holdAdapter != null) {
            for (int i = 0; i < holdAdapter.getData().size(); i++) {
                if (holdAdapter.getData().get(i).getMark().equals(coinBean.getCode())) {
                    holdAdapter.getData().get(i).setActprice(coinBean.getPrice()+"");
                    holdAdapter.notifyItemChanged(i);
                }
            }
        }
    }


    @Override
    public void loadData() {
        smartRefreshHelper.refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static HoldFragment newInstance() {
        HoldFragment fragment = new HoldFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setData(Page<HoldBean> data) {
        if (mPage == 1) {
            holdAdapter.setNewData(data.getRes());
        } else {
            holdAdapter.addData(data.getRes());
        }

    }
}
