package com.sskj.common.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.sskj.common.R;
import com.sskj.common.exception.BreakException;
import com.sskj.common.view.ToolBarLayout;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter> extends ExtendActivity {

    protected P mPresenter;

    /**
     * 锁定竖屏
     */
    private boolean isPortrait = true;


    View contentView;

    protected ToolBarLayout mToolBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isPortrait) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (isImmersion()) {
            setContentView(R.layout.common_immer_layout);
            FrameLayout rootView = findViewById(R.id.rootLayout);
            contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
            rootView.addView(contentView);
            initImmersionBar();
        } else {
            setContentView(R.layout.common_normal_layout);
            ViewStub rootView = findViewById(R.id.rootLayout);
            rootView.setOnInflateListener((stub, inflated) -> {
                contentView = inflated;
            });
            rootView.setLayoutResource(getLayoutId());
            rootView.inflate();
        }
        ButterKnife.bind(this);
        mPresenter = getPresenter();
        mPresenter.attachView(this);
        getLifecycle().addObserver(mPresenter);
        initView();
        initData();
        initEvent();
        initToolBar(contentView);
        loadData();
    }

    /**
     * 递归查找当前activity中的ToolBarLayout,设置返回事件
     *
     * @param view
     */
    private void initToolBar(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof ToolBarLayout) {
                    mToolBarLayout = (ToolBarLayout) viewGroup.getChildAt(i);
                    throw new BreakException();
                } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    initToolBar(viewGroup.getChildAt(i));
                }
            }
        } catch (BreakException e) {
            mToolBarLayout.setLeftButtonOnClickListener(v -> finish());
        }
    }

    protected abstract P getPresenter();

    protected abstract int getLayoutId();


    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.common_status_bar)
                //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    public boolean isPortrait() {
        return isPortrait;
    }

    public void setPortrait(boolean portrait) {
        isPortrait = portrait;
    }

    public boolean isImmersion() {
        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        stopRefresh();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {

    }
}
