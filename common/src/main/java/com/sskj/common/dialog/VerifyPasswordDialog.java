package com.sskj.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.sskj.common.R;
import com.sskj.common.R2;
import com.sskj.common.utils.ClickUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 密码验证弹框，含短信和谷歌验证
 *
 * @author Hey
 */
public class VerifyPasswordDialog extends BottomSheetDialog {

    @BindView(R2.id.title_tv)
    TextView titleTv;
    @BindView(R2.id.cancel_tv)
    TextView cancelTv;
    @BindView(R2.id.ps_edt)
    EditText psEdt;
    @BindView(R2.id.ps_layout)
    LinearLayout psLayout;
    @BindView(R2.id.sms_code_edt)
    EditText smsCodeEdt;
    @BindView(R2.id.get_code_tv)
    TextView getCodeTv;
    @BindView(R2.id.sms_layout)
    LinearLayout smsLayout;
    @BindView(R2.id.google_code_edt)
    EditText googleCodeEdt;
    @BindView(R2.id.past_tv)
    TextView pastTv;
    @BindView(R2.id.google_layout)
    LinearLayout googleLayout;
    @BindView(R2.id.submit)
    Button submit;

    private boolean showPS;
    private boolean showSMS;
    private boolean showGoogle;

    private OnConfirmListener onConfirmListener;
    private DisposableSubscriber<Long> disposableSubscriber;

    public VerifyPasswordDialog(@NonNull Context context) {
        this(context, false, true, false);
    }

    public VerifyPasswordDialog(@NonNull Context context, boolean showPS) {
        this(context, showPS, true, false);
    }

    public VerifyPasswordDialog(@NonNull Context context, boolean showSMS, boolean showGoogle, boolean showPs) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_verify_password, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.showSMS = showSMS;
        this.showGoogle = showGoogle;
        this.showPS = showPs;
        initView();
    }

    private void initView() {
        psLayout.setVisibility(showPS ? View.VISIBLE : View.GONE);
        googleLayout.setVisibility(showGoogle ? View.VISIBLE : View.GONE);
        smsLayout.setVisibility(showSMS ? View.VISIBLE : View.GONE);
        cancelTv.setOnClickListener(v -> {
            dismiss();
        });
        ClickUtil.click(submit, view -> {
            if (onConfirmListener != null) {

                if (showPS) {
                    if (TextUtils.isEmpty(psEdt.getText())) {
                        ToastUtils.show(psEdt.getHint());
                        return;
                    }
                }
                if (showSMS) {
                    if (TextUtils.isEmpty(smsCodeEdt.getText())) {
                        ToastUtils.show(smsCodeEdt.getHint());
                        return;
                    }
                }

                if (showGoogle) {
                    if (TextUtils.isEmpty(googleCodeEdt.getText())) {
                        ToastUtils.show(googleCodeEdt.getHint());
                        return;
                    }
                }

                onConfirmListener.onConfirm(this, psEdt.getText().toString(), smsCodeEdt.getText().toString(), googleCodeEdt.getText().toString());
            }
        });
        //发送验证码
        ClickUtil.click(getCodeTv, view -> {

        });
    }


    public void startTimeDown(TextView getCodeView) {
        getCodeView.setEnabled(false);
        getCodeView.setTextColor(ContextCompat.getColor(getContext(), R.color.common_hint));
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                if (getCodeView != null) {
                    getCodeView.setText(time + getString(R.string.login_get_code_retry));
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onComplete() {
                if (getCodeView != null) {
                    getCodeView.setText(getString(R.string.login_get_verify_code));
                    getCodeView.setEnabled(true);
                    getCodeView.setTextColor(color(R.color.common_white));

                }
                if (!disposableSubscriber.isDisposed()) {
                    disposableSubscriber.dispose();
                    disposableSubscriber = null;
                }

            }
        };

        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);

    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposableSubscriber != null) {
            disposableSubscriber.dispose();
        }
    }

    public interface OnConfirmListener {
        /**
         * 点击确认按钮
         *
         * @param dialog
         */
        void onConfirm(VerifyPasswordDialog dialog, String ps, String sms, String google);
    }


}
