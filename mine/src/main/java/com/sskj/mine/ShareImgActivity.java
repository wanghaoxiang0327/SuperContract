package com.sskj.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.utils.ImgUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * @author Hey
 * Create at  2019/06/25
 */
public class ShareImgActivity extends BaseActivity<ShareImgPresenter> {


    @BindView(R2.id.qr_code_img)
    ImageView qrCodeImg;
    @BindView(R2.id.share_layout)
    FrameLayout shareLayout;

    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_share_img;
    }

    @Override
    public ShareImgPresenter getPresenter() {
        return new ShareImgPresenter();
    }

    @Override
    public void initView() {
        mToolBarLayout.setRightButtonOnClickListener(view -> {
            new RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            ImgUtil.saveImageToGallery(this, shareLayout);
                        }
                    }, e -> {
                        e.printStackTrace();
                    });
        });
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra("url");
        Glide.with(this).load( url).into(qrCodeImg);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, ShareImgActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


}
