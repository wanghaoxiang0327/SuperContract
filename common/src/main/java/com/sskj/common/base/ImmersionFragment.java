package com.sskj.common.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

public class ImmersionFragment extends LazyFragment  {

    private boolean mIsActivityCreated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsActivityCreated) {
            if (immersionBarEnabled()) {
                initImmersionBar();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsActivityCreated = true;
        if (getUserVisibleHint()) {
            if (immersionBarEnabled()) {
                initImmersionBar();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (immersionBarEnabled()) {
            ImmersionBar.with(this).destroy();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getUserVisibleHint()) {
            if (immersionBarEnabled()) {
                initImmersionBar();
            }
        }
    }

    public void initImmersionBar() {

    }

    public boolean immersionBarEnabled() {
        return false;
    }
}
