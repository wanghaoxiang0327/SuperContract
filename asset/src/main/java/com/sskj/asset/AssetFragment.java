package com.sskj.asset;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.sskj.asset.data.AssetData;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.router.RoutePath;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.CoinIcon;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 资产
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class AssetFragment extends BaseFragment<AssetPresenter> {


    @BindView(R2.id.total_asset_tv)
    TextView totalAssetTv;
    @BindView(R2.id.hide_asset_img)
    ImageView hideAssetImg;
    @BindView(R2.id.cny_asset_tv)
    TextView cnyAssetTv;
    @BindView(R2.id.asset_list)
    RecyclerView assetList;
    @BindView(R2.id.content_layout)
    NestedScrollView contentLayout;

    BaseAdapter<AssetData.ResBean.AssetBean> assetAdapter;
    private boolean checkSms;
    private boolean checkGoogle;

    @Override
    public int getLayoutId() {
        return R.layout.asset_fragment_asset;
    }

    @Override
    public AssetPresenter getPresenter() {
        return new AssetPresenter();
    }

    @Override
    public void initView() {

        userViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                checkSms = userBean.getIsStartSms() == 1;
                checkGoogle = userBean.getIsStartGoogle() == 1;
            }
        });

        assetList.setLayoutManager(new LinearLayoutManager(getContext()));
        assetAdapter = new BaseAdapter<AssetData.ResBean.AssetBean>(R.layout.asset_item_asset, null, assetList) {
            @Override
            public void bind(ViewHolder holder, AssetData.ResBean.AssetBean item) {
                holder.setText(R.id.coin_name, item.getPname());
                holder.setImageResource(R.id.coin_icon, CoinIcon.getIcon(item.getMark()));
                holder.setText(R.id.asset_useful, NumberUtils.keepDown(item.getUsable(), 4));
                holder.setText(R.id.asset_frost, NumberUtils.keepDown(item.getFrost(), 4));
                ClickUtil.click(holder.getView(R.id.recharge), view -> {
                    RechargeActivity.start(getContext());
                });
                ClickUtil.click(holder.getView(R.id.transfer), view -> {

                    if (!checkSms && !checkGoogle) {
                        new TipDialog(getContext())
                                .setContent("为了保证您的账户安全，短信验证和谷歌验证方式至少开启一种")
                                .setCancelVisible(View.GONE)
                                .setConfirmListener(dialog -> {
                                    dialog.dismiss();
                                    ARouter.getInstance().build(RoutePath.SECURITY).navigation();
                                })
                                .show();
                    } else {
                        TransferActivity.start(getContext());
                    }

                });
                ClickUtil.click(holder.getView(R.id.cashOut), view -> {
                    if (!checkSms && !checkGoogle) {
                        new TipDialog(getContext())
                                .setContent("为了保证您的账户安全，短信验证和谷歌验证方式至少开启一种")
                                .setCancelVisible(View.GONE)
                                .setConfirmListener(dialog -> {
                                    dialog.dismiss();
                                    ARouter.getInstance().build(RoutePath.SECURITY).navigation();
                                })
                                .show();
                    } else {
                        WithdrawActivity.start(getContext());
                    }

                });
            }
        };
    }


    @Override
    public void initData() {
        wrapRefresh(contentLayout);
        setEnableLoadMore(false);
        //资产明细
        mToolBarLayout.setRightButtonOnClickListener(view -> {
            AssetRecordsActivity.start(getContext());
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void lazyLoad() {
        mPresenter.getAsset();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getAsset();
    }


    public static AssetFragment newInstance() {
        AssetFragment fragment = new AssetFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setAsset(AssetData data) {
        totalAssetTv.setText(NumberUtils.keepDown(data.getRes().getTotal().getTtl_money(), 4));
        cnyAssetTv.setText("≈" + NumberUtils.keepDown(data.getRes().getTotal().getTtl_cnymoney(), 2) + "CNY");
        assetAdapter.setNewData(data.getRes().getAsset());
    }
}
