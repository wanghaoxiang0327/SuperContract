package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.hjq.toast.ToastUtils;
import com.sskj.asset.data.TransferInfo;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.data.CoinAsset;
import com.sskj.common.dialog.SelectCoinDialog;
import com.sskj.common.dialog.VerifyPasswordDialog;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.MoneyValueFilter;
import com.sskj.common.utils.NumberUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 站内转账
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class TransferActivity extends BaseActivity<TransferPresenter> {


    @BindView(R2.id.account_edt)
    EditText accountEdt;
    @BindView(R2.id.select_coin)
    SuperTextView selectCoin;
    @BindView(R2.id.useful_tv)
    TextView usefulTv;
    @BindView(R2.id.fee_tv)
    TextView feeTv;
    @BindView(R2.id.count_edt)
    EditText countEdt;
    @BindView(R2.id.submit)
    Button submit;


    private SelectCoinDialog selectCoinDialog;
    private List<CoinAsset> coinList;
    private String pid;
    private String code;

    double minCount;
    boolean checkSms;
    boolean checkGoogle;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_transfer;
    }

    @Override
    public TransferPresenter getPresenter() {
        return new TransferPresenter();
    }

    @Override
    public void initView() {
        mToolBarLayout.setRightButtonOnClickListener(view -> {
            TransferRecordsActivity.start(this);
        });

        selectCoin.setOnClickListener(view -> {
            if (coinList == null) {
                mPresenter.getCoinAsset(true);
            } else {
                showCoinDialog(coinList);
            }
        });
        countEdt.setFilters(new InputFilter[]{new MoneyValueFilter(4)});

        userViewModel.getUser().observe(this, userBean -> {
            if (userBean != null) {
                checkSms = userBean.getIsStartSms() == 1;
                checkGoogle = userBean.getIsStartGoogle() == 1;
            }
        });

    }

    @Override
    public void initData() {
        ClickUtil.click(submit, view -> {
            if (isEmptyShow(accountEdt)) {
                return;
            }
            if (isEmpty(countEdt)) {
                ToastUtils.show(getString(R.string.asset_transferActivity1));
            }
            double count = Double.parseDouble(getText(countEdt));
            if (count < minCount) {
                ToastUtils.show(getString(R.string.asset_transferActivity2) + minCount);
            }
            new VerifyPasswordDialog(this, checkSms, checkGoogle, true, 5)
                    .setOnConfirmListener((dialog, ps, sms, google) -> {
                        dialog.dismiss();
                        mPresenter.transfer(getText(accountEdt), pid, getText(countEdt), ps, sms, google);
                    }).show();
        });
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
        selectCoinDialog.setData(data);
        selectCoinDialog.show();
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
        mPresenter.getTransferInfo(pid);
        code=coin.getPname();
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, TransferActivity.class);
        context.startActivity(intent);
    }


    public void setTransferInfo(TransferInfo data) {
        usefulTv.setText(getString(R.string.asset_transferActivity3) + NumberUtils.keepDown(data.getUsable(), DigitUtils.getAssetDigit(code)));
        feeTv.setText(getString(R.string.asset_transferActivity4) + data.getSxfee() + getString(R.string.asset_transferActivity5));
        countEdt.setHint(getString(R.string.asset_transferActivity2) + data.getZz_min());
        minCount = data.getZz_min();
    }

    /**
     * 转账成功
     */
    public void transferSuccess() {
        accountEdt.getText().clear();
        countEdt.getText().clear();
    }
}
