package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/26
 */
public class TransferRecordsActivity extends BaseActivity<TransferRecordsPresenter> {


    @BindView(R2.id.records_list)
    RecyclerView recordsList;

    BaseAdapter<AssetRecordsBean> recordsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_transfer_records;
    }

    @Override
    public TransferRecordsPresenter getPresenter() {
        return new TransferRecordsPresenter();
    }

    @Override
    public void initView() {
        recordsList.setLayoutManager(new LinearLayoutManager(this));
        recordsList.addItemDecoration(new DividerLineItemDecoration(this)
                .setDividerColor(color(R.color.common_divider))
                .setFirstDraw(false)
                .setLastDraw(false));
        recordsAdapter = new BaseAdapter<AssetRecordsBean>(R.layout.asset_item_transfer_records, null, recordsList) {
            @Override
            public void bind(ViewHolder holder, AssetRecordsBean item) {

            }
        };
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TransferRecordsActivity.class);
        context.startActivity(intent);
    }


}
