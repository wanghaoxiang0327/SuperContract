package com.sskj.market;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.language.LanguageSPUtil;
import com.sskj.common.language.LocalManageUtil;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.market.data.OrderDetail;

import butterknife.BindView;

/**
 * @author Hey
 * Create at  2019/07/01
 */
public class ProfitShareActivity extends BaseActivity<ProfitSharePresenter> {
    @BindView(R2.id.marketTextview)
    TextView marketTextview;
    @BindView(R2.id.buy_type)
    TextView buyType;
    @BindView(R2.id.deal_price)
    TextView dealPrice;
    @BindView(R2.id.create_price)
    TextView createPrice;
    @BindView(R2.id.coin_name)
    TextView coinName;
    @BindView(R2.id.marketProfit_tv)
    TextView marketProfitTv;
    @BindView(R2.id.qr_code)
    ImageView qrCode;
    @BindView(R2.id.back_img)
    ImageView backImg;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.market_activity_profit_share;
    }

    @Override
    public ProfitSharePresenter getPresenter() {
        return new ProfitSharePresenter();
    }

    @Override
    public void initView() {
        setNavigationBarColor();
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void initData() {
        mPresenter.getOrderDetail(id);
        ClickUtil.click(backImg, view -> finish());
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, ProfitShareActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    public void setOrderDetail(OrderDetail data) {
        coinName.setText(data.getPname());
        marketProfitTv.setText(NumberUtils.keepDown(data.getIncome(), DigitUtils.getDigit(data.getPname())) + data.getPtype());
        createPrice.setText("$" + NumberUtils.keepDown(data.getBuyprice(), DigitUtils.getDigit(data.getPname())));
        dealPrice.setText("$" + NumberUtils.keepDown(data.getSellprice(),DigitUtils.getDigit(data.getPname())));
        Glide.with(this).load(data.getQrc()).into(qrCode);
        if (data.getType() == 1) {
            buyType.setText(getString(R.string.market_buy_up));
            buyType.setBackgroundResource(R.drawable.market_green_bg_5);
        } else {
            buyType.setText(getString(R.string.market_buy_down));
            buyType.setBackgroundResource(R.drawable.market_red_bg_5);
        }
    }

    private void setNavigationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
