package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.sskj.asset.data.TransferRecodsBean;
import com.sskj.asset.data.WithdrawRecordsBean;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.dialog.SelectCoinDialog;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;

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

    SmartRefreshHelper<List<WithdrawRecordsBean>> smartRefreshHelper;

    private List<CoinAsset> coinList;

    private SelectCoinDialog selectCoinDialog;

    private String pid;

    private int size = 10;

    private String type = "cash";

    private Map<Integer, String> statusMap = new HashMap<>();

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
        statusMap.put(1, "待审核");
        statusMap.put(2, "到账中");
        statusMap.put(3, "已拒绝");
        statusMap.put(4, "已到账");
        statusMap.put(5, "失败");
        withdrawRecords.addItemDecoration(new DividerLineItemDecoration(this)
                .setFirstDraw(false)
                .setLastDraw(false)
                .setDividerColor(color(R.color.common_divider)));
        withdrawRecords.setLayoutManager(new LinearLayoutManager(this));
        withdrawRecordsAdapter = new BaseAdapter<WithdrawRecordsBean>(R.layout.asset_item_withdraw_records, null, withdrawRecords) {
            @Override
            public void bind(ViewHolder holder, WithdrawRecordsBean item) {
                holder.setText(R.id.address, item.getQianbao_url())
                        .setText(R.id.count, item.getPrice() + " " + item.getPname())
                        .setText(R.id.crete_time, item.getAddtime())
                        .setText(R.id.check_time, item.getCheck_time())
                        .setText(R.id.status, statusMap.get(item.getState()));
            }
        };

        selectCoin.setOnClickListener(view -> {
            if (coinList == null) {
                mPresenter.getCoinAsset(true);
            } else {
                showCoinDialog(coinList);
            }
        });

    }

    @Override
    public void initData() {
        wrapRefresh(withdrawRecords);
        smartRefreshHelper = new SmartRefreshHelper<>(mRefreshLayout);
        smartRefreshHelper.setDataSource(new DataSource<WithdrawRecordsBean>() {
            @Override
            public Flowable<List<WithdrawRecordsBean>> bindData(int page) {

                return mPresenter.getWithdrawRecords(type, pid, page, size);
            }
        });
        smartRefreshHelper.setAdapter(withdrawRecordsAdapter);
    }


    @Override
    public void loadData() {
        mPresenter.getCoinAsset(false);
    }

    public void showCoinDialog(List<CoinAsset> data) {
        if (selectCoinDialog == null) {
            selectCoinDialog = new SelectCoinDialog(this, (dialog, coin, position) -> {
                changeCoin(coin);
                dialog.dismiss();
            });
        }
//        boolean addAll = true;
//        for (CoinAsset coin : data) {
//            if (coin.getPname().equals("全部")) {
//                addAll = false;
//            }
//        }
//        if (addAll) {
//            CoinAsset coinAsset = new CoinAsset();
//            coinAsset.setPname("全部");
//            coinAsset.setPid("");
//            data.add(0, coinAsset);
//        }
        selectCoinDialog.setData(data);
        selectCoinDialog.show();
    }

    public void setCoinList(List<CoinAsset> data) {
        coinList = data;
        if (data != null && !data.isEmpty()) {
            changeCoin(data.get(0));
        }
    }


    public void changeCoin(CoinAsset coin) {
        pid = coin.getPid();
        selectCoin.setRightString(coin.getPname());
        smartRefreshHelper.refresh();
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, WithdrawRecordsActivity.class);
        context.startActivity(intent);
    }

}
