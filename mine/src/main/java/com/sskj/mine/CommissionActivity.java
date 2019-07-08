package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.mine.data.CommissionBean;
import com.sskj.mine.data.CommissionDetailBean;
import com.sskj.mine.data.CommissionTopBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 佣金明细
 *
 * @author Hey
 * Create at  2019/06/25
 */
public class CommissionActivity extends BaseActivity<CommissionPresenter> {


    BaseAdapter<CommissionTopBean> commissionAdapter;
    BaseAdapter<CommissionDetailBean> commissionDetailAdapter;

    @BindView(R2.id.commission_top_list)
    RecyclerView commissionTopList;
    @BindView(R2.id.detail_list)
    RecyclerView detailList;
    @BindView(R2.id.content_layout)
    LinearLayout contentLayout;

    private int size = 10;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_commission;
    }

    @Override
    public CommissionPresenter getPresenter() {
        return new CommissionPresenter();
    }

    @Override
    public void initView() {
        commissionTopList.setLayoutManager(new GridLayoutManager(this, 2));
        commissionAdapter = new BaseAdapter<CommissionTopBean>(R.layout.mine_item_comission_top, null, commissionTopList) {
            @Override
            public void bind(ViewHolder holder, CommissionTopBean item) {
                holder.setText(R.id.coin_name, item.getCode())
                        .setText(R.id.freeze_tv, NumberUtils.keepDown(item.getForst(), DigitUtils.getAssetDigit(item.getCode())))
                        .setText(R.id.future_tv, NumberUtils.keepDown(item.getExpect(), DigitUtils.getAssetDigit(item.getCode())))
                        .setText(R.id.total_tv, NumberUtils.keepDown(item.getTotal(), DigitUtils.getAssetDigit(item.getCode())));
            }
        };

        detailList.addItemDecoration(new DividerLineItemDecoration(this)
                .setDividerColor(color(R.color.common_divider))
                .setLastDraw(false)
                .setFirstDraw(false));
        detailList.setLayoutManager(new LinearLayoutManager(this));
        commissionDetailAdapter = new BaseAdapter<CommissionDetailBean>(R.layout.mine_item_comission_detail, null, detailList) {
            @Override
            public void bind(ViewHolder holder, CommissionDetailBean item) {
                holder.setText(R.id.user_id, item.getDown_account())
                        .setText(R.id.count, item.getFee() + item.getPname())
                        .setText(R.id.time, TimeFormatUtil.SF_FORMAT_E.format(item.getAddtime() * 1000));
            }
        };


    }

    @Override
    public void initData() {
        wrapRefresh(contentLayout);
    }

    @Override
    public void loadData() {
        mPresenter.getCommsion(page, size);
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.getCommsion(page, size);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        mPresenter.getCommsion(page, size);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CommissionActivity.class);
        context.startActivity(intent);
    }


    public void setCommission(CommissionBean data) {
        commissionAdapter.setNewData(data.getData().getTotal());
        if (page == 1) {
            commissionDetailAdapter.setNewData(data.getData().getList());
        } else {
            commissionDetailAdapter.addData(data.getData().getList());
        }

        if (data.getData().getList() == null || data.getData().getList().isEmpty()) {
            mRefreshLayout.setNoMoreData(true);
        }
    }
}
