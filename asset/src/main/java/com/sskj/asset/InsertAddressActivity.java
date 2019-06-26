package com.sskj.asset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sskj.common.base.BaseActivity;

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

    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InsertAddressActivity.class);
        context.startActivity(intent);
    }

}
