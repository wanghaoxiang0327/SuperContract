package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Hey
 * Create at  2019/06/25
 */
public class BindGoogleVerifyActivity extends BaseActivity<BindGoogleVerifyPresenter> {


    @BindView(R2.id.verify_name)
    TextView verifyName;
    @BindView(R2.id.google_code)
    TextView googleCode;
    @BindView(R2.id.verify_code_name)
    TextView verifyCodeName;
    @BindView(R2.id.edt_verify_code)
    EditText edtVerifyCode;
    @BindView(R2.id.qr_code_img)
    ImageView qrCodeImg;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_bind_googl_everify;
    }

    @Override
    public BindGoogleVerifyPresenter getPresenter() {
        return new BindGoogleVerifyPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, BindGoogleVerifyActivity.class);
        context.startActivity(intent);
    }


}
