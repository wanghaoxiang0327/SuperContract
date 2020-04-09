package com.sskj.common.base;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public interface IBaseView {

    boolean isEmpty(TextView textView);
    boolean isEmptyShow(TextView textView);

    String getText(TextView textView);

    int color(int id);

    Drawable drawable(int id);

    void setText(TextView textView, String text);

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化事件
     */
    void initEvent();

    /**
     * 加载数据,放在这里可以实现下拉刷新
     */
    void loadData();


    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 隐藏loading
     */
    void hideLoading();

}
