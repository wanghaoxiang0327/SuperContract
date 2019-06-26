package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.sskj.asset.data.WithdrawRecordsBean;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提币记录
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class WithdrawRecordsActivity extends BaseActivity<WithdrawRecordsPresenter> {


    @BindView(R2.id.select_coin)
    SuperTextView selectCoin;
    @BindView(R2.id.withdraw_records)
    RecyclerView withdrawRecords;

    BaseAdapter<WithdrawRecordsBean> withdrawRecordsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_withdraw_records;
    }

    @Override
    public WithdrawRecordsPresenter getPresenter() {
        return new WithdrawRecordsPresenter();
    }

    @Override
    public void initView() {
        withdrawRecords.setLayoutManager(new LinearLayoutManager(this));
        withdrawRecordsAdapter=new BaseAdapter<WithdrawRecordsBean>(R.layout.asset_item_withdraw_records,null,withdrawRecords ) {
            @Override
            public void bind(ViewHolder holder, WithdrawRecordsBean item) {

            }
        };
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WithdrawRecordsActivity.class);
        context.startActivity(intent);
    }

}
