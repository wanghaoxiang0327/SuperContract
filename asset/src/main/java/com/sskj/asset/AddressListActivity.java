package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sskj.asset.data.AddressBean;
import com.sskj.common.DividerLineItemDecoration;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.utils.ClickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/26
 */
public class AddressListActivity extends BaseActivity<AddressListPresenter> {

    @BindView(R2.id.add_btc_address)
    ImageView addBtcAddress;
    @BindView(R2.id.btc_address_list)
    RecyclerView btcAddressList;
    @BindView(R2.id.add_eth_address)
    ImageView addEthAddress;
    @BindView(R2.id.eth_address_list)
    RecyclerView ethAddressList;

    BaseAdapter<AddressBean.Address> btcAdapter, ethAdapter;

    private boolean select;


    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_address_list;
    }

    @Override
    public AddressListPresenter getPresenter() {
        return new AddressListPresenter();
    }

    @Override
    public void initView() {
        select = getIntent().getBooleanExtra("select", false);
        btcAddressList.setLayoutManager(new LinearLayoutManager(this));
        btcAddressList.addItemDecoration(new DividerLineItemDecoration(this)
                .setDividerColor(color(R.color.common_divider)));
        ethAddressList.setLayoutManager(new LinearLayoutManager(this));
        ethAddressList.addItemDecoration(new DividerLineItemDecoration(this)
                .setDividerColor(color(R.color.common_divider)));
        btcAdapter = new BaseAdapter<AddressBean.Address>(R.layout.asset_item_address, null, btcAddressList) {
            @Override
            public void bind(ViewHolder holder, AddressBean.Address item) {
                holder.setText(R.id.name_tv, item.getNotes())
                        .setText(R.id.address, item.getQiaobao_url());
                ClickUtil.click(holder.getView(R.id.delete), view -> {
                    mPresenter.deleteAddress(item.getId());
                });
                ClickUtil.click(holder.itemView, view -> {
                    if (select) {
                        Intent intent = new Intent();
                        intent.putExtra("address", item.getQiaobao_url());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };

        ethAdapter = new BaseAdapter<AddressBean.Address>(R.layout.asset_item_address, null, ethAddressList) {
            @Override
            public void bind(ViewHolder holder, AddressBean.Address item) {
                holder.setText(R.id.name_tv, item.getNotes())
                        .setText(R.id.address, item.getQiaobao_url());
                ClickUtil.click(holder.getView(R.id.delete), view -> {
                    mPresenter.deleteAddress(item.getId());
                });

                ClickUtil.click(holder.itemView, view -> {
                    if (select) {
                        Intent intent = new Intent();
                        intent.putExtra("address", item.getQiaobao_url());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
    }

    @Override
    public void initData() {
        ClickUtil.click(addBtcAddress, view -> {
            InsertAddressActivity.start(this, "btc");
        });

        ClickUtil.click(addEthAddress, view -> {
            InsertAddressActivity.start(this, "eth");
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAddressList();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressListActivity.class);
        context.startActivity(intent);
    }

    public void setData(AddressBean data) {
        if (data != null) {
            btcAdapter.setNewData(data.getBtc());
            ethAdapter.setNewData(data.getEth());
        }
    }

    public void deleteSuccess(Object data) {
        mPresenter.getAddressList();
    }
}
