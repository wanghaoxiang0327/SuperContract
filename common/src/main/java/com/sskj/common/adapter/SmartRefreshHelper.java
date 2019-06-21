package com.sskj.common.adapter;

import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shizhefei.mvc.IDataAdapter;
import com.shizhefei.mvc.ILoadViewFactory;
import com.shizhefei.mvc.IRefreshView;
import com.shizhefei.mvc.MVCHelper;

import java.lang.reflect.Method;

/**
 * Created by lv on 18-5-24.
 */

public class SmartRefreshHelper<DATA> extends MVCHelper<DATA> {
    public SmartRefreshHelper(SmartRefreshLayout refreshView) {
        super(new SmartRefreshView(refreshView));
    }

    public SmartRefreshHelper(SmartRefreshLayout refreshView, ILoadViewFactory.ILoadView loadView) {
        super(new SmartRefreshView(refreshView), loadView);
    }

    public SmartRefreshHelper(SmartRefreshLayout refreshView, ILoadViewFactory.ILoadView loadView, ILoadViewFactory.ILoadMoreView loadMoreView) {
        super(new SmartRefreshView(refreshView), loadView, loadMoreView);
    }

    @Override
    public void setAdapter(IDataAdapter<DATA> adapter) {
        super.setAdapter(adapter);
        refresh();
    }

    public static class SmartRefreshView implements IRefreshView {
        SmartRefreshLayout smartRefreshLayout;
        OnRefreshListener onRefreshListener;
        private View view;

        public SmartRefreshView(SmartRefreshLayout smartRefreshLayout) {

            this.smartRefreshLayout = smartRefreshLayout;
            smartRefreshLayout.setEnableLoadMore(false);
            try {
                Method method = smartRefreshLayout.getRefreshHeader().getClass().getMethod("getView");
                method.setAccessible(true);
                view = (View) method.invoke(smartRefreshLayout.getRefreshHeader());

            } catch (Exception e) {
                e.printStackTrace();
            }
            smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            });
        }

        @Override
        public View getContentView() {
            return view;
        }

        @Override
        public View getSwitchView() {
            return smartRefreshLayout;
        }

        @Override
        public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
            this.onRefreshListener = onRefreshListener;

        }

        @Override
        public void showRefreshComplete() {
            smartRefreshLayout.finishRefresh();
        }

        @Override
        public void showRefreshing() {
            smartRefreshLayout.autoRefresh();
        }

    }
}
