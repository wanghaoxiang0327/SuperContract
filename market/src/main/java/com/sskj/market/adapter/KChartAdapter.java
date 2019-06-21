package com.sskj.market.adapter;

import com.github.tifezh.kchartlib.chart.BaseKChartAdapter;
import com.sskj.market.data.ChartUtil;
import com.sskj.market.data.Stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KChartAdapter extends BaseKChartAdapter {
    private List<Stock> datas = new ArrayList<>();

    public KChartAdapter() {

    }

    public List<Stock> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public Date getDate(int position) {
        return new Date(datas.get(position).getDate());
    }

    /**
     * 向头部添加数据
     */
    public void addNewData(List<Stock> data) {
        if (data != null && !data.isEmpty()) {
            ChartUtil.calculate(data);
            datas.addAll(data);
            notifyItemRangeInsertedToLast();
        }
    }


    /**
     * 向尾部添加数据
     */
    public void updateData(List<Stock> data) {
        if (data != null && !data.isEmpty()) {
            ChartUtil.calculate(data);
            datas.clear();
            datas.addAll(0, data);
            notifyDataSetChanged();
        }
    }

    /**
     * 改变某个点的值
     */
    public void changeLastItemClosePrice(float closePrice) {
        notifyLastItemChanged(closePrice);
    }
}
