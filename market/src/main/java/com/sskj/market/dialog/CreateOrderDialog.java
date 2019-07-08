package com.sskj.market.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hey.lib.HeySpinner;
import com.sskj.common.App;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.DigitUtils;
import com.sskj.common.utils.NumberUtils;
import com.sskj.market.MarketDetailActivity;
import com.sskj.market.R;
import com.sskj.market.R2;
import com.sskj.market.TradeInfo;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.TradeCoin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CreateOrderDialog extends BottomSheetDialog {

    @BindView(R2.id.name_tv)
    TextView nameTv;
    @BindView(R2.id.cancel_tv)
    TextView cancelTv;
    @BindView(R2.id.asset_group)
    RadioGroup assetGroup;
    @BindView(R2.id.trade_point_group)
    RadioGroup tradePointGroup;
    @BindView(R2.id.trade_count_group)
    RadioGroup tradeCountGroup;
    @BindView(R2.id.total_price)
    TextView totalPrice;

    @BindView(R2.id.trade_num_spinner)
    HeySpinner tradeNumSpinner;
    @BindView(R2.id.stop_win_tv)
    TextView stopWinTv;
    @BindView(R2.id.stop_loss_tv)
    TextView stopLossTv;
    @BindView(R2.id.price_tv)
    TextView price_tv;
    @BindView(R2.id.submit)
    Button submit;

    private TradeInfo tradeInfo;

    private List<String> nums = new ArrayList<>();

    /**
     * 交易手数
     */
    private int tradeNum;

    /**
     * 交易币种
     */
    private String payCoin;
    /**
     * 目标点位
     */
    private double tradePoint;
    /**
     * 交易量
     */
    private double tradeVolume;

    /**
     * 最新价
     */
    private double price;

    /**
     * 最小变动价*
     */
    private double minChangePrice;

    private boolean isUp;

    private String code;

    private String pid;


    public CreateOrderDialog(@NonNull Context context, TradeInfo data, boolean up, String code) {
        super(context, R.style.BottomSheetDialog);
        setContentView(R.layout.market_dialog_create_order);
        setCancelable(false);
        ButterKnife.bind(this);
        RxBus.getDefault().registerPre(this);
        this.tradeInfo = data;
        this.isUp = up;
        this.code = code;
        initView();

    }

    @SuppressLint("CheckResult")
    private void initView() {
        for (int i = 1; i < 11; i++) {
            nums.add(i + App.INSTANCE.getString(R.string.market_createOrderDialog1));
        }
        tradeNumSpinner.setOnSelectListener(position -> {
            tradeNum = position + 1;
            computeTotal();
        });
        tradeNumSpinner.attachData(nums);
        //设置币种名称
        nameTv.setText(code);
        //初始化交易资产
        initTradeCoin();
        //初始化目标点位
        Flowable.fromIterable(tradeInfo.getTradeCoins())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter(tradeCoin -> tradeCoin.getMark().equals(code)).
                subscribe(tradeCoin -> {
                    initTradePoint(tradeCoin.getAim_point());
                    minChangePrice = tradeCoin.getMin_price();
                    price = Double.parseDouble(tradeCoin.getPrice());
                    price_tv.setText(tradeCoin.getPrice());
                    pid = tradeCoin.getPid();
                }, Throwable::printStackTrace);


        if (isUp) {
            submit.setText(App.INSTANCE.getString(R.string.market_buy_up));
            submit.setBackgroundResource(R.drawable.market_green_bg_50);
        } else {
            submit.setText(App.INSTANCE.getString(R.string.market_buy_down));
            submit.setBackgroundResource(R.drawable.market_red_bg_50);
        }
        cancelTv.setOnClickListener(v -> {
            dismiss();
        });

        ClickUtil.click(submit, view -> {
            if (listener != null) {
                listener.createOrder(isUp ? 1 : 2, payCoin, tradeVolume, tradeNum, pid, tradePoint);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(CoinBean data) {
        if (data != null) {
            if (data.getCode().equals(code)) {
                price = data.getPrice();
                price_tv.setText(NumberUtils.keepDown(data.getPrice(), DigitUtils.getDigit(data.getCode())));
                if (data.getChange() > 0) {
                    price_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.market_green));
                } else {
                    price_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.market_red));
                }
                computeTotal();
            }
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        RxBus.getDefault().unregister(this);
    }

    /**
     * 初始化交易币种
     */
    private void initTradeCoin() {
        assetGroup.removeAllViews();
        for (int i = 0; i < tradeInfo.getCoinAssets().size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER;
            if (i != 0) {
                layoutParams.setMargins(16, 0, 0, 0);
            } else {
                radioButton.setChecked(true);
            }
            radioButton.setTag(i);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.market_tip_5));
            radioButton.setText(tradeInfo.getCoinAssets().get(i).getPname());
            radioButton.setEnabled(tradeInfo.getCoinAssets().get(i).getYue() != 0);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.market_color));
            radioButton.setButtonDrawable(null);
            assetGroup.addView(radioButton);
        }

        assetGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = assetGroup.findViewById(i);
                int position = (int) button.getTag();
                payCoin = tradeInfo.getCoinAssets().get(position).getPname();
                initTradeCount(tradeInfo.getCoinAssets().get(position).getAim_point());
            }
        });
        assetGroup.check(assetGroup.getChildAt(0).getId());
    }


    /**
     * 初始化交易量
     *
     * @param counts
     */
    private void initTradeCount(List<String> counts) {
        tradeCountGroup.removeAllViews();
        for (int i = 0; i < counts.size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(16, 0, 0, 0);
            radioButton.setTag(i);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.market_tip_5));
            radioButton.setText(counts.get(i));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.common_text));
            radioButton.setButtonDrawable(null);
            tradeCountGroup.addView(radioButton);
        }

        tradeCountGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = tradeCountGroup.findViewById(i);
                tradeVolume = Double.parseDouble(button.getText().toString());
                computeTotal();
            }
        });
        tradeCountGroup.check(tradeCountGroup.getChildAt(0).getId());
    }

    /**
     * 初始化目标点位
     *
     * @param points
     */
    private void initTradePoint(List<String> points) {
        tradePointGroup.removeAllViews();
        for (int i = 0; i < points.size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(16, 0, 0, 0);
            radioButton.setTag(i);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.market_tip_5));
            radioButton.setText(points.get(i));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.common_text));
            radioButton.setButtonDrawable(null);
            tradePointGroup.addView(radioButton);
        }

        tradePointGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = tradePointGroup.findViewById(i);
                tradePoint = Double.parseDouble(button.getText().toString());
                computeTotal();
            }
        });
        tradePointGroup.check(tradePointGroup.getChildAt(0).getId());
    }

    /**
     * 计算交易总额  交易量*交易手数
     * 计算止盈止损
     * 买涨方向
     * 止盈 = 最新价 + 目标点位*最小变动价
     * 止损 = 最新价 – 目标点位*最小变动价
     * 买跌方向
     * 止盈 = 最新价 - 目标点位*最小变动价
     * 止损 = 最新价 + 目标点位*最小变动价
     */
    private void computeTotal() {
        double stopWin = 0;
        double stopLoss = 0;
        if (isUp) {
            stopWin = price + tradePoint * minChangePrice;
            stopLoss = price - tradePoint * minChangePrice;
        } else {
            stopWin = price - tradePoint * minChangePrice;
            stopLoss = price + tradePoint * minChangePrice;
        }
        stopWinTv.setText(NumberUtils.keepDown(stopWin, DigitUtils.getDigit(code)));
        stopLossTv.setText(NumberUtils.keepDown(stopLoss, DigitUtils.getDigit(code)));
        double total = tradeVolume * tradeNum;
        totalPrice.setText(App.INSTANCE.getString(R.string.market_createOrderDialog4) + NumberUtils.keepDown(total, DigitUtils.getAssetDigit(payCoin)));
    }

    public void setListener(CreateOrderListener listener) {
        this.listener = listener;
    }


    public interface CreateOrderListener {
        void createOrder(int type, String ptype, Double unit_num, int num, String pid, Double aim_point);
    }

    private CreateOrderListener listener;
}
