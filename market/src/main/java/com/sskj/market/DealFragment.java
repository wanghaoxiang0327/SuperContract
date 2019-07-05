package com.sskj.market;

import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;

import com.sskj.common.http.Page;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.market.R;
import com.sskj.market.data.HoldBean;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;

/**
 * Create at  2019/06/26
 */
public class DealFragment extends BaseFragment<DealPresenter> {
    BaseAdapter<HoldBean> holdAdapter;
    @BindView(R2.id.records_list)
    RecyclerView recordsList;

    private int size = 10;

    SmartRefreshHelper<List<HoldBean>> smartRefreshHelper;
    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_deal;
    }

    @Override
    public DealPresenter getPresenter() {
        return new DealPresenter();
    }

    @Override
    public void initView() {
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
                        .setText(R.id.stop_win_str, getString(R.string.market_dealFragment3))
                        .setText(R.id.stop_loss_str, getString(R.string.market_dealFragment4))
                        .setText(R.id.stop_win_tv, NumberUtils.keepDown(item.getSellprice(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.stop_loss_tv, NumberUtils.keepDown(item.getIncome(), DigitUtils.getDigit(item.getMark())));

                if (item.getType() == 1) {
                    holder.setTextColor(R.id.buy_type_tv, color(R.color.market_green));
                } else {
                    holder.setTextColor(R.id.buy_type_tv, color(R.color.market_red));
                }

                //已平仓
                if (item.getState() == 2) {
                    holder.setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_E.format(item.getSelltime() * 1000));
                    if (item.getPc_type() == 1) {
                        holder.setVisible(R.id.share_tv, true);
                        holder.setText(R.id.new_pirce_tv, getString(R.string.market_dealFragment5));
                        holder.setTextColor(R.id.new_pirce_tv, color(R.color.market_green));
                        ClickUtil.click(holder.getView(R.id.share_tv), view -> {
                            ProfitShareActivity.start(getContext(), item.getId());
                        });
                    } else {
                        holder.setVisible(R.id.share_tv, false);
                        holder.setText(R.id.new_pirce_tv, getString(R.string.market_dealFragment6));
                        holder.setTextColor(R.id.new_pirce_tv, color(R.color.market_red));
                        ClickUtil.click(holder.getView(R.id.share_tv), view -> {
                        });
                    }
                    holder.getView(R.id.new_pirce_tv).setPadding(0, 0, 0, 0);
                    holder.setBackgroundColor(R.id.new_pirce_tv, Color.TRANSPARENT);
                } else {
                    holder.setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_E.format(item.getAddtime() * 1000));
                }


            }
        };
    }


    @Override
    public void initData() {
        wrapRefresh(recordsList);
        smartRefreshHelper=new SmartRefreshHelper<>(mRefreshLayout);
        smartRefreshHelper.setDataSource(new DataSource<HoldBean>() {
            @Override
            public Flowable<List<HoldBean>> bindData(int page) {
                return  mPresenter.getOrder(2, page, size);
            }
        });
        smartRefreshHelper.setAdapter(holdAdapter);
    }

    @Override
    public void loadData() {
        smartRefreshHelper.refresh();
    }



    public static DealFragment newInstance() {
        DealFragment fragment = new DealFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


}
