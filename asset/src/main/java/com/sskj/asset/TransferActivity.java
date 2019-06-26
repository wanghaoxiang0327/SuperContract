package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.sskj.common.base.BaseActivity;

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
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TransferActivity.class);
        context.startActivity(intent);
    }


}
