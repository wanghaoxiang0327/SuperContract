package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.asset.data.TransferRecodsBean;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;

/**
 * @author Hey
 * Create at  2019/06/26
 */
public class TransferRecordsActivity extends BaseActivity<TransferRecordsPresenter> {


    @BindView(R2.id.records_list)
    RecyclerView recordsList;

    BaseAdapter<TransferRecodsBean> recordsAdapter;


    SmartRefreshHelper<List<TransferRecodsBean>> smartRefreshHelper;

    private String pid;

    private int size = 10;

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
        recordsAdapter = new BaseAdapter<TransferRecodsBean>(R.layout.asset_item_transfer_records, null, recordsList) {
            @Override
            public void bind(ViewHolder holder, TransferRecodsBean item) {
                holder.setText(R.id.type_name, item.getMemo())
                        .setText(R.id.count_tv, NumberUtils.keepDown(item.getPrice(), DigitUtils.getDigit(item.getPname())) + " " + item.getPname())
                        .setText(R.id.time_tv, TimeFormatUtil.SF_FORMAT_E.format(item.getAddtime() * 1000))
                        .setText(R.id.account_tv, "ID: " + item.getAccountId());
            }
        };
    }

    @Override
    public void initData() {
        wrapRefresh(recordsList);
        smartRefreshHelper = new SmartRefreshHelper<>(mRefreshLayout);
        smartRefreshHelper.setDataSource(new DataSource<TransferRecodsBean>() {
            @Override
            public Flowable<List<TransferRecodsBean>> bindData(int page) {

                return mPresenter.getTransferRecord(pid, page, size);
            }
        });
        smartRefreshHelper.setAdapter(recordsAdapter);
    }


    @Override
    public void loadData() {
        smartRefreshHelper.refresh();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TransferRecordsActivity.class);
        context.startActivity(intent);
    }


}
