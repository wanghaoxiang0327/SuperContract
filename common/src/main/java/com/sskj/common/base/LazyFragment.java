package com.sskj.common.base;

import android.support.v4.app.Fragment;

public class LazyFragment extends Fragment {


    private boolean isResume;

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if (getUserVisibleHint() && !isFirst) {
            onVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onInVisible();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setUserVisibleHint(!hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirst) {
                lazyLoad();
                isFirst = false;
            } else {
                onVisible();
            }
        } else {
            if (isFirst){
                onInVisible();
            }
        }
    }

    public void lazyLoad() {

    }

    public void onVisible() {

    }

    public void onInVisible() {

    }

}
