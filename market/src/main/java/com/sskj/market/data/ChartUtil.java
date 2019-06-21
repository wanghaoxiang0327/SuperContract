package com.sskj.market.data;

import com.github.tifezh.kchartlib.chart.DataHelper;

import java.util.List;

public class ChartUtil {
    public static void calculate(List<Stock> datas){
        DataHelper.calculateMA(datas);
        DataHelper.calculateMACD(datas);
        DataHelper.calculateBOLL(datas);
        DataHelper.calculateRSI(datas);
        DataHelper.calculateKDJ(datas);
        DataHelper.calculateWR(datas);
        DataHelper.calculateVolumeMA(datas);
    }
}
