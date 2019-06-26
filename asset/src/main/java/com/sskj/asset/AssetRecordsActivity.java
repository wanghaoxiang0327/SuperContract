package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/26
 */
public class AssetRecordsActivity extends BaseActivity<AssetRecordsPresenter> {


    @BindView(R2.id.select_coin_name)
    TextView selectCoinName;
    @BindView(R2.id.select_type_name)
    TextView selectTypeName;
    @BindView(R2.id.asset_records_list)
    RecyclerView assetRecordsList;


    BaseAdapter<AssetRecordsBean> recordsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_asset_records;
    }

    @Override
    public AssetRecordsPresenter getPresenter() {
        return new AssetRecordsPresenter();
    }

    @Override
    public void initView() {
        assetRecordsList.setLayoutManager(new LinearLayoutManager(this));
        assetRecordsList.addItemDecoration(new DividerLineItemDecoration(this)
                .setDividerColor(color(R.color.common_divider))
                .setFirstDraw(false)
                .setLastDraw(false));
        recordsAdapter = new BaseAdapter<AssetRecordsBean>(R.layout.asset_item_asset_records, null, assetRecordsList) {
            @Override
            public void bind(ViewHolder holder, AssetRecordsBean item) {

            }
        };
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {
        List<AssetRecordsBean> data = new ArrayList<>();
        data.add(new AssetRecordsBean());
        data.add(new AssetRecordsBean());
        data.add(new AssetRecordsBean());
        recordsAdapter.setNewData(data);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AssetRecordsActivity.class);
        context.startActivity(intent);
    }


}
