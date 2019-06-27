package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.http.HttpResult;
import com.sskj.common.http.RxUtils;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.utils.ClickUtil;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.CoinIcon;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;

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
        marketList.setLayoutManager(new LinearLayoutManager(getContext()));
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
