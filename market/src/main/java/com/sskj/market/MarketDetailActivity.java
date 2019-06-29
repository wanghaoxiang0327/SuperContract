package com.sskj.market;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.hjq.toast.ToastUtils;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.dialog.SelectCoinDialog;
import com.sskj.common.router.RoutePath;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;
import com.sskj.common.utils.ClickUtil;
import com.sskj.common.utils.NumberUtils;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.TradeCoin;
import com.sskj.market.dialog.CreateOrderDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author Hey
 * Create at  2019/05/22
 */
@Route(path = RoutePath.MARKET_DETAIL)
public class MarketDetailActivity extends BaseActivity<MarketDetailPresenter> {

    @BindView(R2.id.tv_price)
    TextView tvPrice;
    @BindView(R2.id.tv_high)
    TextView tvHigh;
    @BindView(R2.id.tv_change_rate)
    TextView tvChangeRate;
    @BindView(R2.id.tvCny)
    TextView tvCny;
    @BindView(R2.id.tv_low)
    TextView tvLow;
    @BindView(R2.id.chart_tabLayout)
    TabLayout chartTabLayout;
    @BindView(R2.id.chart_norm)
    TextView chartNorm;
    @BindView(R2.id.buy_up_btn)
    Button buyUpBtn;
    @BindView(R2.id.buy_down_btn)
    Button buyDownBtn;

    ArrayList<CustomTabEntity> chartTabs = new ArrayList<>();


    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    String[] goodsType = {"1min", "1min", "5min", "15min", "30min", "day"};
    //指标
    private boolean isUpToggle = true;
    private boolean isDownToggle = true;
    private KChartPop kChartPop;

    @Autowired
    CoinBean coinBean;

    private String code;

    private CreateOrderDialog createOrderDialog;

    @Override
    public int getLayoutId() {
        return R.layout.market_activity_market_detail;
    }

    @Override
    public MarketDetailPresenter getPresenter() {
        return new MarketDetailPresenter();
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        if (coinBean != null) {
            code = coinBean.getCode();
            mToolBarLayout.setTitle(coinBean.getCode());
            mToolBarLayout.mTextTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable(R.mipmap.market_down), null);
            mToolBarLayout.mTextTitle.setOnClickListener(v -> {
                new SelectCoinDialog(MarketDetailActivity.this, (dialog, coin) -> {
                    mToolBarLayout.setTitle(coin.getName());
                    dialog.dismiss();
                }).show();
            });
            updateUI(coinBean);
        }
        mToolBarLayout.setRightButtonOnClickListener(v -> {
            TransactionRecordsActivity.start(this);
        });
        chartTabs.add(new TabItem(getString(R.string.market_time), 0, 0));
        chartTabs.add(new TabItem("1M", 0, 0));
        chartTabs.add(new TabItem("5M", 0, 0));
        chartTabs.add(new TabItem("15M", 0, 0));
        chartTabs.add(new TabItem("30M", 0, 0));
        chartTabs.add(new TabItem(getString(R.string.market_day), 0, 0));
        fragmentList.add(ChartFragment.newInstance(code, goodsType[0], true));
        fragmentList.add(ChartFragment.newInstance(code, goodsType[1], false));
        fragmentList.add(ChartFragment.newInstance(code, goodsType[2], false));
        fragmentList.add(ChartFragment.newInstance(code, goodsType[3], false));
        fragmentList.add(ChartFragment.newInstance(code, goodsType[4], false));
        fragmentList.add(ChartFragment.newInstance(code, goodsType[5], false));
        chartTabLayout.setTabData(chartTabs, getSupportFragmentManager(), R.id.chart_content, fragmentList);
        initPoint();
    }

    @Override
    public void initData() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.order_content, ProfitOrderFragment.newInstance());
        ft.commitAllowingStateLoss();

        //买涨下单
        ClickUtil.click(buyUpBtn, view -> {
            mPresenter.getTradeInfo(true);
        });
        //买跌下单
        ClickUtil.click(buyDownBtn, view -> {
            mPresenter.getTradeInfo(false);
        });
    }

    @Override
    public void loadData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(CoinBean data) {
        if (data != null) {
            if (data.getCode().equals(code)) {
                tvPrice.setText(NumberUtils.keep4(data.getPrice()));
                tvCny.setText(String.format("≈%s CNY", NumberUtils.keep2(data.getCnyPrice())));
                tvChangeRate.setText(data.getChangeRate());
                tvLow.setText(NumberUtils.keep4(data.getLow()));
                tvHigh.setText(NumberUtils.keep4(data.getHigh()));
                tvPrice.setTextColor(data.isUp() ? color(R.color.market_green) : color(R.color.market_red));
                tvChangeRate.setTextColor(data.isUp() ? color(R.color.market_green) : color(R.color.market_red));
            }
        }
    }


    private void initPoint() {
        ClickUtil.click(chartNorm, v -> {
            if (kChartPop == null) {
                kChartPop = new KChartPop(this);
                View contentView = kChartPop.getContentView();
                TextView tvMa = contentView.findViewById(R.id.tvMa);
                TextView tvBoll = contentView.findViewById(R.id.tvBoll);
                TextView tvMacd = contentView.findViewById(R.id.tvMacd);
                TextView tvKdj = contentView.findViewById(R.id.tvKdj);
                TextView tvRsi = contentView.findViewById(R.id.tvRsi);
                TextView tvWr = contentView.findViewById(R.id.tvWr);
                ImageView ivUpToggle = contentView.findViewById(R.id.ivUpToggle);
                ImageView ivDownToggle = contentView.findViewById(R.id.ivDownToggle);
                ivUpToggle.setImageResource(R.mipmap.market_icon_show);
                ivDownToggle.setImageResource(R.mipmap.market_icon_show);
                ClickUtil.click(tvMa, view -> {
                    ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().setMainDrawMaShow();
                    ivUpToggle.setImageResource(R.mipmap.market_icon_show);
                    kChartPop.dismiss();
                });
                ClickUtil.click(tvBoll, view -> {
                    ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().setMainDrawBollShow();
                    ivUpToggle.setImageResource(R.mipmap.market_icon_show);
                    kChartPop.dismiss();

                });
                ClickUtil.click(ivUpToggle, view -> {
                    if (isUpToggle) {
                        isUpToggle = false;
                        ivUpToggle.setImageResource(R.mipmap.market_icon_hide);

                        ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().setMainDrawNoneShow();
                    } else {
                        isUpToggle = true;
                        ivUpToggle.setImageResource(R.mipmap.market_icon_show);
                        ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().setMainDrawMaShow();
                    }
                    kChartPop.dismiss();

                });
                ClickUtil.click(tvMacd, view -> {
                    ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().changeMACD();
                    kChartPop.dismiss();

                });
                ClickUtil.click(tvKdj, view -> {
                    ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().changeKDJ();
                    kChartPop.dismiss();

                });
                ClickUtil.click(tvRsi, view -> {
                    ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().changeRSI();
                    kChartPop.dismiss();

                });
                ClickUtil.click(tvWr, view -> {
                    ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().changeWR();
                    kChartPop.dismiss();

                });
                ClickUtil.click(ivDownToggle, view -> {
                    if (isDownToggle) {
                        isDownToggle = false;
                        ivDownToggle.setImageResource(R.mipmap.market_icon_hide);
                        ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().setDrawDown(isDownToggle);
                    } else {
                        isDownToggle = true;
                        ivDownToggle.setImageResource(R.mipmap.market_icon_show);

                        ((ChartFragment) fragmentList.get(chartTabLayout.getCurrentTab())).getKChartView().setDrawDown(isDownToggle);
                    }
                    kChartPop.dismiss();

                });
                kChartPop.setBackground(0);
            }
            if (kChartPop.isShowing()) {
                kChartPop.dismiss();
            } else {
                kChartPop.showPopupWindow(chartTabLayout);
            }
        });
    }


    public static void start(Context context, CoinBean coinBean) {
        Intent intent = new Intent(context, MarketDetailActivity.class);
        intent.putExtra("coinBean", coinBean);
        context.startActivity(intent);
    }


    public void setCoinInfo(TradeInfo data, boolean up) {
        createOrderDialog = new CreateOrderDialog(this, data, up, code);
        createOrderDialog.show();
        createOrderDialog.setListener((type, ptype, unit_num, num, pid, aim_point) -> {
            mPresenter.createOrder(type, ptype, unit_num, num, pid, aim_point);
        });
    }

    public void setTradeCoinInfo(List<TradeCoin> data) {


    }

    public void createOrderSuccess() {
        ToastUtils.show("创建订单成功");
        createOrderDialog.dismiss();
    }
}
