package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sskj.asset.data.AssetRecordsBean;
import com.sskj.common.data.AssetType;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.dialog.SelectCoinDialog;
import com.sskj.common.dialog.SelectCoinDialog.OnSelectListener;
import com.sskj.common.dialog.SelectTypeDialog;
import com.sskj.common.language.LocalManageUtil;
import com.sskj.common.mvc.DataSource;
import com.sskj.common.mvc.SmartRefreshHelper;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;

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

    SmartRefreshHelper<List<AssetRecordsBean>> smartRefreshHelper;

    private String pid;
    private int type;
    private int size = 10;

    private List<CoinAsset> coinList;
    private List<AssetType> typeList;

    private SelectCoinDialog selectCoinDialog;

    private SelectTypeDialog selectTypeDialog;


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
                holder.setText(R.id.type, item.getMemo())
                        .setText(R.id.count, NumberUtils.keepDown(item.getPrice(), DigitUtils.getAssetDigit(item.getPtype())) + item.getPtype())
                        .setText(R.id.time, TimeFormatUtil.SF_FORMAT_E.format(item.getAddtime() * 1000));
            }
        };
    }

    @Override
    public void initData() {
        wrapRefresh(assetRecordsList);
        smartRefreshHelper = new SmartRefreshHelper<>(mRefreshLayout);
        smartRefreshHelper.setDataSource(new DataSource<AssetRecordsBean>() {
            @Override
            public Flowable<List<AssetRecordsBean>> bindData(int page) {

                return mPresenter.getAssetDetail(pid, type + "", page, size);
            }
        });
        smartRefreshHelper.setAdapter(recordsAdapter);
        selectCoinName.setOnClickListener(view -> {
            if (coinList == null) {
                mPresenter.getCoinAsset(true);
            } else {
                showCoinDialog(coinList);
            }
        });
        selectTypeName.setOnClickListener(view -> {
            if (typeList == null) {
                mPresenter.getAssetType(true, LocalManageUtil.getLanguage(this));
            } else {
                showTypeDialog(typeList);
            }
        });
    }

    @Override
    public void loadData() {
        smartRefreshHelper.refresh();
        mPresenter.getCoinAsset(false);
        mPresenter.getAssetType(false, LocalManageUtil.getLanguage(this));
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AssetRecordsActivity.class);
        context.startActivity(intent);
    }


    public void setCoinList(List<CoinAsset> data) {
        coinList = data;
    }

    public void showCoinDialog(List<CoinAsset> data) {
        if (selectCoinDialog == null) {
            selectCoinDialog = new SelectCoinDialog(this, (dialog, coin, position) -> {
                pid = coin.getPid();
                selectCoinName.setText(coin.getPname());
                smartRefreshHelper.refresh();
                dialog.dismiss();
            });
        }
        boolean addAll = true;
        for (CoinAsset coin : data) {
            if (coin.getPname().equals(getString(R.string.asset_assetRecordsActivity1))) {
                addAll = false;
            }
        }
        if (addAll) {
            CoinAsset coinAsset = new CoinAsset();
            coinAsset.setPname(getString(R.string.asset_assetRecordsActivity1));
            coinAsset.setPid("");
            data.add(0, coinAsset);
        }
        selectCoinDialog.setData(data);
        selectCoinDialog.show();
    }


    public void showTypeDialog(List<AssetType> data) {
        if (selectTypeDialog == null) {
            selectTypeDialog = new SelectTypeDialog(this, (dialog, coin) -> {
                type = coin.getId();
                selectTypeName.setText(coin.getTitle());
                smartRefreshHelper.refresh();
                dialog.dismiss();
            });
        }
        boolean addAll = true;
        for (AssetType coin : data) {
            if (coin.getTitle().equals(getString(R.string.asset_assetRecordsActivity1))) {
                addAll = false;
            }
        }
        if (addAll) {
            AssetType coinAsset = new AssetType();
            coinAsset.setTitle(getString(R.string.asset_assetRecordsActivity1));
            coinAsset.setId(0);
            data.add(0, coinAsset);
        }
        selectTypeDialog.setData(data);
        selectTypeDialog.show();
    }


    public void setTypeList(List<AssetType> data) {
        typeList = data;
    }
}
