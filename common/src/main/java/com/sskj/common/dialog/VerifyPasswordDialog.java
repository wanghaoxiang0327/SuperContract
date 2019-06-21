package com.sskj.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sskj.common.R;
import com.sskj.common.R2;
import com.sskj.common.utils.ClickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 密码验证弹框，含短信和谷歌验证
 * @author Hey
 */
public class VerifyPasswordDialog extends BottomSheetDialog {

    @BindView(R2.id.title_tv)
    TextView titleTv;
    @BindView(R2.id.cancel_tv)
    TextView cancelTv;
    @BindView(R2.id.get_code_tv)
    TextView getCodeTv;
    @BindView(R2.id.past_tv)
    TextView pastTv;
    @BindView(R2.id.sms_code_edt)
    EditText smsCodeEdt;
    @BindView(R2.id.google_code_edt)
    EditText googleCodeEdt;
    @BindView(R2.id.ps_layout)
    LinearLayout psLayout;
    @BindView(R2.id.google_layout)
    LinearLayout googleLayout;
    @BindView(R2.id.submit)
    Button submit;

    private boolean showPS;
    private boolean showGoogle;

    private OnConfirmListener onConfirmListener;

    public VerifyPasswordDialog(@NonNull Context context) {
        this(context, true, true);
    }

    public VerifyPasswordDialog(@NonNull Context context, boolean showPS) {
        this(context, showPS, true);
    }

    public VerifyPasswordDialog(@NonNull Context context, boolean showPS, boolean showGoogle) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_verify_password, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.showPS = showPS;
        this.showGoogle = showGoogle;
        initView();
    }

    private void initView() {
        psLayout.setVisibility(showPS ? View.VISIBLE : View.GONE);
        googleLayout.setVisibility(showGoogle ? View.VISIBLE : View.GONE);
        cancelTv.setOnClickListener(v -> {
            dismiss();
        });
        ClickUtil.click(submit, view -> {
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm(this);
            }
        });
    }

    public interface OnConfirmListener {
        /**
         * 点击确认按钮
         *
         * @param dialog
         */
        void onConfirm(VerifyPasswordDialog dialog);
    }


}
