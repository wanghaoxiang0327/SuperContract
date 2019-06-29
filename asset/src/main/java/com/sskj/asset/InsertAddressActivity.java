package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sskj.common.BaseApplication;
import com.sskj.common.ScanActivity;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.utils.ClickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加收货地址
 *
 * @author Hey
 * Create at  2019/06/26
 */
public class InsertAddressActivity extends BaseActivity<InsertAddressPresenter> {


    @BindView(R2.id.address_edt)
    EditText addressEdt;
    @BindView(R2.id.select_address)
    ImageView selectAddress;
    @BindView(R2.id.remark_edt)
    EditText remarkEdt;
    @BindView(R2.id.submit)
    Button submit;

    private String type;

    private int SCAN_CODE = 1000;

    @Override
    public int getLayoutId() {
        return R.layout.asset_activity_insert_address;
    }

    @Override
    public InsertAddressPresenter getPresenter() {
        return new InsertAddressPresenter();
    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void initData() {
        ClickUtil.click(submit, view -> {
            if (isEmptyShow(addressEdt)) {
                return;
            }
            if (isEmptyShow(remarkEdt)) {
                return;
            }
            mPresenter.insertAddress(getText(addressEdt), getText(remarkEdt), BaseApplication.getMobile(), type);
        });
        ClickUtil.click(selectAddress, view -> {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivityForResult(intent, SCAN_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SCAN_CODE) {
                String address=data.getStringExtra("result");
                addressEdt.setText(address);
            }
        }
    }

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, InsertAddressActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    public void insertSuccess() {
        finish();
    }
}
