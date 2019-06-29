package com.sskj.common;

import com.hjq.toast.ToastUtils;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.ScanPresenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.sskj.common.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * @author Hey
 * Create at  2019/06/29
 */
public class ScanActivity extends BaseActivity<ScanPresenter> implements QRCodeView.Delegate {

    ZXingView scanView;

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_scan;
    }

    @Override
    public ScanPresenter getPresenter() {
        return new ScanPresenter();
    }

    @Override
    public void initView() {


        scanView = findViewById(R.id.scan_view);
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scanView.setDelegate(this);

        new RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(isGrant -> {
                    if (isGrant) {
                        //打开摄像头
                        scanView.startCamera();
                        // 显示扫描框，并开始识别
                        scanView.startSpotAndShowRect();
                    } else {
                        ToastUtils.show("请打开相机权限");
                        finish();
                    }
                });

    }

    @Override
    protected void onStop() {
        super.onStop();
        scanView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanView.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        scanView.getScanBoxView().setTipText("打开相机错误，请重试");
    }
}
