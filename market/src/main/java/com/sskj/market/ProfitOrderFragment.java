package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sskj.common.ChangeCoinEvent;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.Page;
import com.sskj.common.http.RxUtils;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.socket.WebSocket;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.market.data.OrderBean;
import com.sskj.market.data.ProfitSocketBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 盈利单
 * Create at  2019/06/27
 */
public class ProfitOrderFragment extends BaseFragment<ProfitOrderPresenter> {

    @BindView(R2.id.orderList)
    RecyclerView orderList;

    BaseAdapter<OrderBean> orderAdapter;

    private WebSocket webSocket;

    private String code;
    private String pid;

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
        if (getArguments() != null) {
            code = getArguments().getString("code");
            pid = getArguments().getString("pid");
        }
        getLifecycle().addObserver(RxBus.getDefault(this, true));

        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new BaseAdapter<OrderBean>(R.layout.market_item_profit_order, null, orderList,true) {
            @Override
            public void bind(ViewHolder holder, OrderBean item) {
                holder.setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_H.format(item.getSelltime() * 1000))
                        .setText(R.id.type_tv, item.getType() == 1 ? getString(R.string.market_buy_up) : getString(R.string.market_buy_down))
                        .setText(R.id.profit_tv, NumberUtils.keepDown(item.getIncome(), DigitUtils.getAssetDigit(item.getPtype())) + " " + item.getPtype())
                        .setText(R.id.price_tv, NumberUtils.keepDown(item.getSellprice(), DigitUtils.getDigit(code)));
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
        JSONObject message = new JSONObject();
        message.put("code", code);
        webSocket = new WebSocket(BaseHttpConfig.WS_PROFIT, "profit", message.toString(), false);
        webSocket.setListener(message1 -> {
            try {
                ProfitSocketBean orderBean = JSON.parseObject(message1, ProfitSocketBean.class);
                if (orderBean != null) {
                    Observable.just(orderBean)
                            .compose(RxUtils.transform())
                            .map(profitSocketBean -> {
                                return orderBean.getRecs();
                            })
                            .subscribe(orderBeans -> {
                                orderAdapter.setNewData(orderBeans);
                            }, e -> {
                                e.printStackTrace();
                            });

                }
            } catch (Exception e) {

            }
        });
    }

    @Override
    public void loadData() {
        mPresenter.getProfitOrder(pid);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeCoin(ChangeCoinEvent event) {
        this.code = event.getCode();
        this.pid=event.getPid();
        mPresenter.getProfitOrder(event.getPid());
        if (webSocket != null) {
            JSONObject message = new JSONObject();
            message.put("code", code);
            webSocket.sendMessage(message.toString());
        }
    }


    public static ProfitOrderFragment newInstance(String code,String  pid) {
        ProfitOrderFragment fragment = new ProfitOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("pid", pid);
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setData(Page<OrderBean> data) {
        orderAdapter.setNewData(data.getRes());
    }
}
