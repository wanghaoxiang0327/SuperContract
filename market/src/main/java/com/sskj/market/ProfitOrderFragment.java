package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.http.Page;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.market.data.OrderBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 盈利单
 * Create at  2019/06/27
 */
public class ProfitOrderFragment extends BaseFragment<ProfitOrderPresenter> {

    @BindView(R2.id.orderList)
    RecyclerView orderList;

    BaseAdapter<OrderBean> orderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_profit_order;
    }

    @Override
    public ProfitOrderPresenter getPresenter() {
        return new ProfitOrderPresenter();
    }

    @Override
    public void initView() {
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new BaseAdapter<OrderBean>(R.layout.market_item_profit_order, null, orderList) {
            @Override
            public void bind(ViewHolder holder, OrderBean item) {
                holder.setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_H.format(item.getSelltime() * 1000))
                        .setText(R.id.type_tv, item.getType() == 1 ? "买涨" : "买跌")
                        .setText(R.id.profit_tv, item.getIncome()+" "+item.getPtype())
                        .setText(R.id.price_tv, item.getSellprice());
                if (item.getType() == 1) {
                    holder.setTextColor(R.id.type_tv, color(R.color.market_green));
                } else {
                    holder.setTextColor(R.id.type_tv, color(R.color.market_red));
                }
            }
        };
    }


    @Override
    public void initData() {

    }

    @Override
    public void loadData() {
        mPresenter.getProfitOrder();
    }


    public static ProfitOrderFragment newInstance() {
        ProfitOrderFragment fragment = new ProfitOrderFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setData(Page<OrderBean> data) {
        orderAdapter.setNewData(data.getRes());
    }
}
