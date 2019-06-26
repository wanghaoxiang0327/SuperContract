package com.sskj.asset;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.asset.data.AssetBean;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.utils.ClickUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    BaseAdapter<AssetBean> assetAdapter;


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
        assetList.setLayoutManager(new LinearLayoutManager(getContext()));
        assetAdapter = new BaseAdapter<AssetBean>(R.layout.asset_item_asset, null, assetList) {
            @Override
            public void bind(ViewHolder holder, AssetBean item) {

                ClickUtil.click(holder.getView(R.id.recharge), view -> {
                    RechargeActivity.start(getContext());
                });
                ClickUtil.click(holder.getView(R.id.transfer), view -> {
                    TransferActivity.start(getContext());
                });
                ClickUtil.click(holder.getView(R.id.cashOut), view -> {
                    WithdrawActivity.start(getContext());
                });
            }
        };
    }


    @Override
    public void initData() {
        //资产明细
        mToolBarLayout.setRightButtonOnClickListener(view -> {
            AssetRecordsActivity.start(getContext());
        });
    }

    @Override
    public void loadData() {
        List<AssetBean> data = new ArrayList<>();
        data.add(new AssetBean());
        data.add(new AssetBean());
        data.add(new AssetBean());
        assetAdapter.setNewData(data);
    }


    public static AssetFragment newInstance() {
        AssetFragment fragment = new AssetFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


}
