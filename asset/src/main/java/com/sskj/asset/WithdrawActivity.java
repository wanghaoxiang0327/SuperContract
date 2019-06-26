package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.Coin;
import com.sskj.common.dialog.SelectCoinDialog;
import com.sskj.common.utils.ClickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提币
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> {


    @BindView(R2.id.select_coin)
    SuperTextView selectCoin;
    @BindView(R2.id.useful_tv)
    TextView usefulTv;
    @BindView(R2.id.address_edt)
    EditText addressEdt;
    @BindView(R2.id.select_address)
    ImageView selectAddress;
    @BindView(R2.id.count_edt)
    EditText countEdt;
    @BindView(R2.id.coin_name)
    TextView coinName;
    @BindView(R2.id.all)
    TextView all;
    @BindView(R2.id.fee_tv)
    TextView feeTv;
    @BindView(R2.id.arrive_count)
    TextView arriveCount;
    @BindView(R2.id.submit)
    Button submit;

    private final int SELECT_ADDRESS = 1003;

    private String unit;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_withdraw;
    }

    @Override
    public WithdrawPresenter getPresenter() {
        return new WithdrawPresenter();
    }

    @Override
    public void initView() {
        mToolBarLayout.setRightButtonOnClickListener(view -> {
            WithdrawRecordsActivity.start(this);
        });
    }

    @Override
    public void initData() {
        ClickUtil.click(selectAddress, view -> {
            Intent intent = new Intent(this, AddressListActivity.class);
            startActivityForResult(intent, SELECT_ADDRESS);
        });
        //选择币种
        ClickUtil.click(selectCoin, view -> {
            new SelectCoinDialog(this, (dialog, coin) -> {
                changeCoin(coin);
                dialog.dismiss();
            }).show();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_ADDRESS) {
                String address = data.getStringExtra("address");
                if (address != null) {
                    addressEdt.setText(address);
                    addressEdt.setSelection(address.length());
                }
            }
        }
    }

    public void changeCoin(Coin coin) {
        selectCoin.setRightString(coin.getName());
        coinName.setText(coin.getName());
        unit = coin.getName();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }


}
