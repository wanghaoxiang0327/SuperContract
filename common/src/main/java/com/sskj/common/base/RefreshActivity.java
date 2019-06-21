package com.sskj.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sskj.common.R;

/**
 * 下拉刷新Activity
 *
 * @author Hey
 */
public class RefreshActivity extends AppCompatActivity {

    private boolean enableRefresh = true;

    private boolean enableLoadMore = true;

    private SmartRefreshLayout mRefreshLayout;


    /**
     * 侵入模式
     *
     * @param parent
     */
    public void inject(ViewGroup parent) {
        if (parent != null) {
            initRefreshLayout();
            if (parent instanceof LinearLayout || parent instanceof FrameLayout) {
                if (parent.getChildCount() == 1) {
                    View childView = parent.getChildAt(0);
                    parent.removeView(childView);
                    mRefreshLayout.addView(childView);
                    parent.addView(mRefreshLayout);
                } else {
                    throw new RuntimeException("该View下有且只有有一个子View");
                }
            }
        }

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
        View rootView = LayoutInflater.from(this).inflate(R.layout.common_refresh_layout, null);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        mRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
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
}
