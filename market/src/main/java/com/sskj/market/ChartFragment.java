package com.sskj.market;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.github.tifezh.kchartlib.chart.KChartView;
import com.sskj.common.ChangeCoinEvent;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.utils.NumberUtils;
import com.sskj.common.utils.TimeFormatUtil;
import com.sskj.market.adapter.KChartAdapter;
import com.sskj.market.data.CoinBean;
import com.sskj.market.data.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;


/**
 * @author Hey
 * Create at  2019/05/22
 */
public class ChartFragment extends BaseFragment<ChartPresenter> {

    @BindView(R2.id.chartView)
    KChartView chartView;
    private KChartAdapter mAdapter;
    private String goodsType;
    private String code;
    private boolean mIsMinute;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(RxBus.getDefault(this, true));
        code = getArguments().getString("code");
        goodsType = getArguments().getString("goodsType");
        mIsMinute = getArguments().getBoolean("isMinute");
    }


    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_chart;
    }

    @Override
    public ChartPresenter getPresenter() {
        return new ChartPresenter();
    }

    @Override
    public void initView() {
        chartView.showLoading();
        chartView.setDrawTabView(true);
        // 设置k线颜色 涨
        chartView.setMainUpColor(R.color.market_green);
        // 设置k线颜色 跌
        chartView.setMainDownColor(R.color.market_red);
        chartView.setGridRows(4);
        chartView.getmCurrentPricePaint().setTextSize(30);
        chartView.setGridColumns(4);
        chartView.setVolumeMaGone(true);
        chartView.setDrawMinuteStyle(mIsMinute);
        chartView.setDrawDown(false);
        chartView.setShader(Color.parseColor("#23305b"),Color.parseColor("#1a2644"),Color.parseColor("#162137"),1000);
        if (goodsType.equals("day")) {
            chartView.setDateTimeFormatter(date -> TimeFormatUtil.SF_FORMAT_K.format(date));
        } else {
            chartView.setDateTimeFormatter(date -> TimeFormatUtil.SF_FORMAT_D.format(date));
        }
        chartView.setValueFormatter(v -> NumberUtils.keepDown(v, 4));
    }

    @Override
    public void initData() {
        mAdapter = new KChartAdapter();
        chartView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void lazyLoad() {
        mPresenter.getStockInfo(goodsType, code);
    }

    @Override
    public void onVisible() {
        mPresenter.getStockInfo(goodsType, code);
    }

    /**
     * 长连接更新
     *
     * @param data
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void socketUpdate(CoinBean data) {
        if (data.getCode().equals(code) && mAdapter.getCount() > 0) {
            Stock lastItem = (Stock) mAdapter.getItem(mAdapter.getCount() - 1);
            try {
                long timeStamp = dateFormat.parse(data.getDate() + " " + data.getTime()).getTime();
                if (isAdd(timeStamp, lastItem.getTimestamp() * 1000)) {
                    List<Stock> stocks = new ArrayList<>();
                    Stock stock = copyStock(data);
                    stock.setOpen(lastItem.getClosePrice());
                    stock.setVolume(lastItem.getVolume());
                    stocks.add(stock);
                    mAdapter.addNewData(stocks);
                } else {
                    if (data.getPrice() > lastItem.getHigh()) {
                        lastItem.setHigh(data.getPrice());
                    }
                    if (data.getPrice() < lastItem.getLow()) {
                        lastItem.setLow(data.getPrice());
                    }
                    lastItem.setClose(data.getPrice());
                    mAdapter.getDatas().set(mAdapter.getCount() - 1, lastItem);
                    mAdapter.changeLastItemClosePrice((float) data.getPrice());
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }


    private Stock copyStock(CoinBean newStock) {
        Stock stock = new Stock();
        try {
            long timeStamp = dateFormat.parse(newStock.getDate() + " " + newStock.getTime()).getTime();
            stock.setCode(newStock.getCode());
            stock.setClosePrice((float) newStock.getClose());
            stock.setTimestamp(timeStamp / 1000);
            stock.setHigh(newStock.getPrice());
            stock.setLow(newStock.getPrice());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stock;
    }


    /**
     * 比较时间 是否添加新的数据
     *
     * @param newTime
     * @param lastTime
     * @return
     */
    private boolean isAdd(long newTime, long lastTime) {
        boolean add = false;
        newTime = newTime / 1000;
        lastTime = lastTime / 1000;
        long time = newTime - lastTime;
        if (goodsType.equals("minute")) {
            add = time >= 60;
        } else if (goodsType.equals("minute1")) {
            add = time >= 60;
        } else if (goodsType.equals("minute5")) {
            add = time >= 5 * 60;
        } else if (goodsType.equals("minute15")) {
            add = time >= 15 * 60;
        } else if (goodsType.equals("minute30")) {
            add = time >= 30 * 60;
        } else if (goodsType.equals("minute60")) {
            add = time >= 60 * 60;
        } else if (goodsType.equals("hour4")) {
            add = time >= 4 * 60 * 60;
        } else if (goodsType.equals("day")) {
            add = time >= 24 * 60 * 60;
        }
        return add;
    }


    /**
     * @param code      币种代码
     * @param goodsType K线类型
     * @param isMinute  是否是分时线
     * @return
     */
    public static ChartFragment newInstance(String code, String goodsType, boolean isMinute) {
        ChartFragment chartFragment = new ChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("goodsType", goodsType);
        bundle.putBoolean("isMinute", isMinute);
        chartFragment.setArguments(bundle);
        return chartFragment;
    }


    public void setChartData(List<Stock> data) {
        if (data != null) {
            if (chartView!=null){
                Collections.reverse(data);
                chartView.hideLoading();
                mAdapter.updateData(data);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeCoin(ChangeCoinEvent event) {
        this.code = event.getCode();
        mPresenter.getStockInfo(goodsType, code);
    }

    public KChartView getKChartView() {
        return chartView;
    }

}
