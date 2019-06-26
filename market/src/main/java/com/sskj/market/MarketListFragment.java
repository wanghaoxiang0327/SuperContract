package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.CoinIcon;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;

/**
 * @author Hey
 * Create at  2019/05/29
 */
public class MarketListFragment extends BaseFragment<MarketListPresenter> {


    @BindView(R2.id.market_list)
    RecyclerView marketList;
    @BindView(R2.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    BaseAdapter<CoinBean> adapter;
    SmartRefreshHelper<List<CoinBean>> smartHelper;

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
        adapter = new BaseAdapter<CoinBean>(R.layout.market_item_coin_list, null, marketList) {
            @Override
            public void bind(ViewHolder holder, CoinBean item) {
                holder.setText(R.id.coin_name, item.getName())
                        .setText(R.id.coin_price, item.getPrice()+"")
                        .setText(R.id.coin_change_rate, item.getChangeRate());
                if (item.getChange() > 0) {
                    holder.setTextColor(R.id.coin_price, color(R.color.market_green));
                    holder.setBackgroundRes(R.id.coin_change_rate, R.drawable.market_green_bg);
                } else {
                    holder.setTextColor(R.id.coin_price, color(R.color.market_red));
                    holder.setBackgroundRes(R.id.coin_change_rate, R.drawable.market_red_bg);
                }
                holder.setImageResource(R.id.coin_img, CoinIcon.getIcon(item.getCode()));
            }
        };
    }


    @Override
    public void initData() {
        smartRefresh.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefresh.setRefreshFooter(new ClassicsFooter(getContext()));
        smartHelper = new SmartRefreshHelper<>(smartRefresh);
        smartHelper.setDataSource(new DataSource<CoinBean>() {
            @Override
            public Flowable<List<CoinBean>> bindData(int page) {
                return mPresenter.getMarketList("");
            }
        });
        smartHelper.setAdapter(adapter);
    }

    @Override
    public void loadData() {
        smartHelper.refresh();
    }

    public static MarketListFragment newInstance() {
        MarketListFragment fragment = new MarketListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


}
