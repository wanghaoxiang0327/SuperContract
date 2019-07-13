package com.sskj.common.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.ImmersionBar;
import com.sskj.common.AppManager;
import com.sskj.common.BaseApplication;
import com.sskj.common.CommonConfig;
import com.sskj.common.R;
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.exception.BreakException;
import com.sskj.common.exception.LogoutException;
import com.sskj.common.language.LocalManageUtil;
import com.sskj.common.router.RoutePath;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.user.model.UserViewModel;
import com.sskj.common.utils.SpUtil;
import com.sskj.common.view.ToolBarLayout;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class BaseActivity<P extends BasePresenter> extends ExtendActivity {

    protected P mPresenter;

    /**
     * 锁定竖屏
     */
    private boolean isPortrait = true;

    View contentView;

    protected ToolBarLayout mToolBarLayout;

    private DisposableSubscriber<Long> disposableSubscriber;

    protected UserViewModel userViewModel;

    TipDialog tipDialog;

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
        initToolBar(contentView);
        getLifecycle().addObserver(RxBus.getDefault(this, true));
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initView();
        initData();
        initEvent();
        loadData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
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
                .statusBarDarkFont(false, 0.2f)
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
        if (disposableSubscriber != null) {
            disposableSubscriber.dispose();
        }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutException e) {
        if (tipDialog==null){
            tipDialog = new TipDialog(this)
                    .setContent(e.getMessage())
                    .setCancelVisible(View.GONE)
                    .setConfirmListener(dialog -> {
                        dialog.dismiss();
                        SpUtil.exit(BaseApplication.getMobile());
                        userViewModel.clear();
                        ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
                        AppManager.getInstance().finishAllLogin();
                    });
        }
        tipDialog.setCancelable(false);
        tipDialog.show();
    }

    public void startTimeDown(TextView getCodeView) {
        getCodeView.setEnabled(false);
        getCodeView.setTextColor(color(R.color.common_hint));
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                if (getCodeView != null) {
                    getCodeView.setText(time + getString(R.string.common_baseActivity1));
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onComplete() {
                if (getCodeView != null) {
                    getCodeView.setText(getString(R.string.common_baseActivity2));
                    getCodeView.setEnabled(true);
                    getCodeView.setTextColor(color(R.color.common_white));

                }
                if (!disposableSubscriber.isDisposed()) {
                    disposableSubscriber.dispose();
                    disposableSubscriber = null;
                }

            }
        };

        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);

    }
}
