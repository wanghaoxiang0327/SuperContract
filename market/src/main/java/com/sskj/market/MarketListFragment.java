package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.tifezh.kchartlib.chart.draw.MainDraw;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.utils.ClickUtil;
import com.sskj.market.data.CoinBean;
import com.sskj.common.utils.CoinIcon;

import java.util.List;

import butterknife.BindView;

/**
 * @author Hey
 * Create at  2019/05/29
 */
public class MarketListFragment extends BaseFragment<MarketListPresenter> {


    @BindView(R2.id.market_list)
    RecyclerView marketList;

    BaseAdapter<CoinBean> adapter;


    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_market_list;
    }

    @Override
    public MarketListPresenter getPresenter() {
        return new MarketListPresenter();
    }

    @Override
    public void initView() {
        getLifecycle().addObserver(RxBus.getDefault(this, true));
        marketList.setLayoutManager(new LinearLayoutManager(getContext()));
        marketList.getItemAnimator().setChangeDuration(0);
        marketList.addItemDecoration(new DividerLineItemDecoration(getContext())
                .setLastDraw(false)
                .setFirstDraw(false)
                .setDividerColor(color(R.color.common_divider))
        );
        adapter = new BaseAdapter<CoinBean>(R.layout.market_item_coin_list, null, marketList) {
            @Override
            public void bind(ViewHolder holder, CoinBean item) {
                if (item.getCode().contains("/")) {
                    holder.setText(R.id.coin_name, item.getCode().split("/")[0]);
                } else {
                    holder.setText(R.id.coin_name, item.getCode());
                }
                holder.setText(R.id.coin_price, item.getPrice() + "")
                        .setText(R.id.coin_change_rate, item.getChangeRate());
                if (item.isUp()) {
                    holder.setTextColor(R.id.coin_price, color(R.color.market_green));
                    holder.setBackgroundRes(R.id.coin_change_rate, R.drawable.market_green_bg);
                } else {
                    holder.setTextColor(R.id.coin_price, color(R.color.market_red));
                    holder.setBackgroundRes(R.id.coin_change_rate, R.drawable.market_red_bg);
                }
                holder.setImageResource(R.id.coin_img, CoinIcon.getIcon(item.getCode()));
                ClickUtil.click(holder.itemView, view -> {
                    MarketDetailActivity.start(getContext(), item);
                });
            }
        };
    }


    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCoin(CoinBean coinBean) {
        if (adapter != null) {
            for (int i = 0; i < adapter.getData().size(); i++) {
                if (adapter.getData().get(i).getCode().equals(coinBean.getCode())) {
                    coinBean.setPid(adapter.getData().get(i).getPid());
                    adapter.getData().set(i, coinBean);
                    adapter.notifyItemChanged(i);
                }
            }
        }
    }


    public static MarketListFragment newInstance() {
        MarketListFragment fragment = new MarketListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setData(List<CoinBean> result) {
        adapter.setNewData(result);
    }


}
