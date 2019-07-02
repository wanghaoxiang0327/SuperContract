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
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.market.data.HoldBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Flowable;

/**
 * Create at  2019/06/26
 */
public class HoldFragment extends BaseFragment<HoldPresenter> {


    BaseAdapter<HoldBean> holdAdapter;
    @BindView(R2.id.records_list)
    RecyclerView recordsList;


    private int size = 10;

    SmartRefreshHelper<List<HoldBean>> smartRefreshHelper;

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
        recordsList.setLayoutManager(new LinearLayoutManager(getContext()));
        recordsList.addItemDecoration(new DividerLineItemDecoration(getContext())
                .setDividerColor(color(R.color.common_dark))
                .setPaintWidth(10)
                .setFirstDraw(false)
                .setLastDraw(false)
        );

        holdAdapter = new BaseAdapter<HoldBean>(R.layout.market_item_hold, null, recordsList,false) {
            @Override
            public void bind(ViewHolder holder, HoldBean item) {
                holder.setText(R.id.name_tv, item.getMark())
                        .setText(R.id.buy_type_tv, item.getType() == 1 ? "买涨" : "买跌")
                        .setText(R.id.pay_coin_tv, item.getPtype())
                        .setText(R.id.price_tv, NumberUtils.keepDown(item.getBuyprice(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.total_price_tv, item.getTotal_num())
                        .setText(R.id.point_tv, item.getAim_point())
                        .setText(R.id.stop_win_tv, NumberUtils.keepDown(item.getStopwin(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.stop_loss_tv, NumberUtils.keepDown(item.getStoploss(), DigitUtils.getDigit(item.getMark())))
                        .setText(R.id.new_pirce_tv, "最新价 " + NumberUtils.keepDown(item.getActprice(), DigitUtils.getDigit(item.getMark())))
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
        smartRefreshHelper=new SmartRefreshHelper<>(mRefreshLayout);
        smartRefreshHelper.setDataSource(new DataSource<HoldBean>() {
            @Override
            public Flowable<List<HoldBean>> bindData(int page) {

                return  mPresenter.getOrder(1, page, size);
            }
        });
        smartRefreshHelper.setAdapter(holdAdapter);
    }

    @Override
    public void loadData() {
       smartRefreshHelper.refresh();
    }


    public static HoldFragment newInstance() {
        HoldFragment fragment = new HoldFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


//    public void setData(Page<HoldBean> data) {
//        if (page == 1) {
//            holdAdapter.setNewData(data.getRes());
//        } else {
//            holdAdapter.addData(data.getRes());
//        }
//
//    }
}
