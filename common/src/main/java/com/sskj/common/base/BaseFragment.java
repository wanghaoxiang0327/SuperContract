package com.sskj.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sskj.common.R;
import com.sskj.common.view.ToolBarLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends ExtendFragment {

    protected View rootView;

    protected P mPresenter;


    private SmartRefreshLayout mRefreshLayout;

    private boolean enableRefresh = true;

    private boolean enableLoadMore = true;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getPresenter();
        mPresenter.attachView(this);
        initView();
        initData();
        initEvent();
        loadData();
    }

    protected abstract int getLayoutId();

    protected abstract P getPresenter();

    @Override
    public void initEvent() {

    }


    /**
     * 包裹模式 在view的外层包裹上RefreshLayout
     *
     * @param view
     */
    public void wrapRefresh(View view) {
        if (view != null && view.getParent() != null) {
            initRefreshLayout();
            ViewGroup parentView = (ViewGroup) view.getParent();
            parentView.removeView(view);
            mRefreshLayout.addView(view);
            parentView.addView(mRefreshLayout);
        }
    }


    public void initRefreshLayout() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.common_refresh_layout, null);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        mRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        mRefreshLayout.setEnableRefresh(enableRefresh);
        mRefreshLayout.setEnableLoadMore(enableLoadMore);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            onRefresh(refreshLayout);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            onLoadMore(refreshLayout);
        });
    }

    public void onRefresh(RefreshLayout refreshLayout) {

    }

    public void onLoadMore(RefreshLayout refreshLayout) {

    }

    public void stopRefresh() {
        if (mRefreshLayout!=null){
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadMore();
        }
    }

    public void setEnableRefresh(boolean enableRefresh) {
        this.enableRefresh = enableRefresh;
        mRefreshLayout.setEnableRefresh(enableRefresh);
    }


    public void setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
        mRefreshLayout.setEnableLoadMore(enableLoadMore);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        stopRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }






}
