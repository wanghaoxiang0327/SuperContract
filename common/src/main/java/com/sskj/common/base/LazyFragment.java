package com.sskj.common.base;

import android.support.v4.app.Fragment;

public class LazyFragment extends Fragment {


    private boolean isResume;

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if (getUserVisibleHint()&&!isFirst){
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
        if (isResume && isVisibleToUser && isFirst) {
            lazyLoad();
            isFirst = false;
        }
        if (!isVisibleToUser) {
            onInVisible();
        } else {
            if (!isFirst) {
                onVisible();
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
