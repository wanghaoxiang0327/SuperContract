package com.sskj.common.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.ImmersionOwner;

public class ImmersionProxy {
    /**
     * 要操作的Fragment对象
     */
    private Fragment mFragment;
    /**
     * 沉浸式实现接口
     */
    private ImmersionOwner mImmersionOwner;
    /**
     * Fragment的view是否已经初始化完成
     */
    private boolean mIsActivityCreated;



    public ImmersionProxy(Fragment fragment) {
        this.mFragment = fragment;
        if (fragment instanceof ImmersionOwner) {
            this.mImmersionOwner = (ImmersionOwner) fragment;
        } else {
            throw new IllegalArgumentException("Fragment请实现ImmersionOwner接口");
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (mFragment != null) {
            if (mFragment.getUserVisibleHint()) {
                if (mIsActivityCreated) {
                        if (mImmersionOwner.immersionBarEnabled()) {
                            mImmersionOwner.initImmersionBar();
                        }
                }
            }
        }
    }



    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mIsActivityCreated = true;
        if (mFragment != null && mFragment.getUserVisibleHint()) {
            if (mImmersionOwner.immersionBarEnabled()) {
                mImmersionOwner.initImmersionBar();
            }
        }
    }



    public void onDestroy() {
        if (mFragment != null && mFragment.getActivity() != null && mImmersionOwner.immersionBarEnabled()) {
            ImmersionBar.with(mFragment).destroy();
        }
        mFragment = null;
        mImmersionOwner = null;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (mFragment != null && mFragment.getUserVisibleHint()) {
            if (mImmersionOwner.immersionBarEnabled()) {
                mImmersionOwner.initImmersionBar();
            }
        }
    }




}
