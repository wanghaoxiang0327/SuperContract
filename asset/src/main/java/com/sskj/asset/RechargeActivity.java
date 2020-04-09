package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.dialog.Coin;
import com.sskj.common.dialog.SelectCoinDialog;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpResult;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 充币
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class RechargeActivity extends BaseActivity<RechargePresenter> {

    @BindView(R2.id.select_coin)
    SuperTextView selectCoin;
    @BindView(R2.id.qr_code_img)
    ImageView qrCodeImg;
    @BindView(R2.id.recharge_address_tv)
    TextView rechargeAddressTv;
    @BindView(R2.id.recharge_tip)
    TextView rechargeTip;
    @BindView(R2.id.copy)
    Button copy;
    private SelectCoinDialog selectCoinDialog;
    private List<CoinAsset> coinList;
    private String pid;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_recharge;
    }

    @Override
    public RechargePresenter getPresenter() {
        return new RechargePresenter();
    }

    @Override
    public void initView() {
        selectCoin.setOnClickListener(view -> {
            if (coinList == null) {
                mPresenter.getCoinAsset(true);
            } else {
                showCoinDialog(coinList);
            }
        });
       mToolBarLayout.setRightButtonOnClickListener(v -> {
           WithdrawRecordsActivity.start(this,"recharge");
       });
    }

    @Override
    public void initData() {
        mPresenter.getCoinAsset(false);
    }


    public void showCoinDialog(List<CoinAsset> data) {
        if (selectCoinDialog == null) {
            selectCoinDialog = new SelectCoinDialog(this, (dialog, coin, position) -> {
                changeCoin(coin);
                dialog.dismiss();
            });
        }
        selectCoinDialog.setData(data);
        selectCoinDialog.show();
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        context.startActivity(intent);
    }


    public void setCoinList(List<CoinAsset> data) {
        if (data != null && !data.isEmpty()) {
            coinList = data;
            changeCoin(data.get(0));
        }

    }

    public void changeCoin(CoinAsset coin) {
        pid = coin.getPid();
        selectCoin.setRightString(coin.getPname());
        mPresenter.getRechargeInfo(pid);
        rechargeTip.setText(getString(R.string.asset_rechargeActivity1) + coin.getPname() + getString(R.string.asset_rechargeActivity2));
    }


    public void setRechargeInfo(Map<String, String> result) {
        Glide.with(this).load(BaseHttpConfig.BASE_URL + "/" + result.get("qrc")).into(qrCodeImg);
        rechargeAddressTv.setText(result.get("url"));
    }
}
