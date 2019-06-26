package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.sskj.asset.data.AddressBean;
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


    BaseAdapter<AddressBean> btcAdapter, ethAdapter;

    private final int INSERT_BTC = 1000;
    private final int INSERT_ETH = 1001;

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
        btcAddressList.setLayoutManager(new LinearLayoutManager(this));
        ethAddressList.setLayoutManager(new LinearLayoutManager(this));

        btcAdapter = new BaseAdapter<AddressBean>(R.layout.asset_item_address, null, btcAddressList) {
            @Override
            public void bind(ViewHolder holder, AddressBean item) {


            }
        };

        ethAdapter = new BaseAdapter<AddressBean>(R.layout.asset_item_address, null, ethAddressList) {
            @Override
            public void bind(ViewHolder holder, AddressBean item) {

            }
        };
    }

    @Override
    public void initData() {
        ClickUtil.click(addBtcAddress, view -> {
            Intent intent = new Intent(this, InsertAddressActivity.class);
            intent.putExtra("type", "BTC");
            startActivityForResult(intent, INSERT_BTC);
        });

        ClickUtil.click(addEthAddress, view -> {
            Intent intent = new Intent(this, InsertAddressActivity.class);
            intent.putExtra("type", "ETH");
            startActivityForResult(intent, INSERT_ETH);
        });

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressListActivity.class);
        context.startActivity(intent);
    }

}
