package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
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
        wrapRefresh(contentLayout);
        commissionTopList.setLayoutManager(new GridLayoutManager(this, 2));
        commissionAdapter = new BaseAdapter<CommissionTopBean>(R.layout.mine_item_comission_top, null, commissionTopList) {
            @Override
            public void bind(ViewHolder holder, CommissionTopBean item) {

            }
        };

        detailList.setLayoutManager(new LinearLayoutManager(this));
        commissionDetailAdapter = new BaseAdapter<CommissionDetailBean>(R.layout.mine_item_comission_detail, null, detailList) {
            @Override
            public void bind(ViewHolder holder, CommissionDetailBean item) {

            }
        };


    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CommissionActivity.class);
        context.startActivity(intent);
    }


}
