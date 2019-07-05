package com.sskj.supercontrct;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sskj.asset.AssetFragment;
import com.sskj.common.AppManager;
import com.sskj.common.BaseApplication;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.base.BasePresenter;
import com.sskj.common.base.NormalActivity;
import com.sskj.common.data.VersionBean;
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.dialog.VersionUpdateDialog;
import com.sskj.common.exception.LogoutException;
import com.sskj.common.http.BaseHttpConfig;
import com.sskj.common.http.HttpResult;
import com.sskj.common.router.RoutePath;
import com.sskj.common.rxbus.RxBus;
import com.sskj.common.rxbus.Subscribe;
import com.sskj.common.rxbus.ThreadMode;
import com.sskj.common.socket.WebSocket;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;
import com.sskj.common.tab.TabSelectListener;
import com.sskj.common.utils.SpUtil;
import com.sskj.market.data.CoinBean;
import com.sskj.mine.MineFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Hey
 */
@Route(path = RoutePath.MAIN)
public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.main_tab_layout)
    TabLayout mainTabLayout;

    ArrayList<CustomTabEntity> mainTabs = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    private WebSocket marketWebSocket;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    @Override
    public void initView() {
        mainTabs.add(new TabItem(getString(R.string.app_mainActivity1), R.mipmap.tab_home, R.mipmap.tab_home_p));
        mainTabs.add(new TabItem(getString(R.string.app_mainActivity2), R.mipmap.tab_news, R.mipmap.tab_news_p));
        mainTabs.add(new TabItem(getString(R.string.app_mainActivity3), R.mipmap.tab_asset, R.mipmap.tab_asset_p));
        mainTabs.add(new TabItem(getString(R.string.app_mainActivity4), R.mipmap.tab_mine, R.mipmap.tab_mine_p));
        fragments.add(HomeFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
        fragments.add(AssetFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        mainTabLayout.setTabData(mainTabs, getSupportFragmentManager(), R.id.main_content, fragments);
    }

    @Override
    public void initData() {
        JSONObject message = new JSONObject();
        message.put("code", "all");
        marketWebSocket = new WebSocket(BaseHttpConfig.WS_URL, "market", message.toString(), false);
        marketWebSocket.setListener(message1 -> {
            try {
                CoinBean coinBean = JSON.parseObject(message1, CoinBean.class);
                if (coinBean != null) {
                    RxBus.getDefault().postPre(coinBean);
                }
            } catch (Exception e) {

            }
        });
    }

    @Override
    public void initEvent() {
        mainTabLayout.setOnTabSelectListener(new TabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                if (position == 2) {
                    if (!BaseApplication.isLogin()) {
                        ARouter.getInstance().build(RoutePath.LOGIN_LOGIN).navigation();
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean onTabReselect(int position) {

                return true;
            }
        });
    }


    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    public void loadData() {
        mPresenter.checkVersion(BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        marketWebSocket.closeWebSocket();
    }


    @SuppressLint("CheckResult")
    public void checkPermission() {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (!aBoolean) {
                        new TipDialog(this)
                                .setContent("请先同意相关权限,以使App正常运行")
                                .setConfirmListener(dialog -> {
                                    checkPermission();
                                })
                                .setCancelListener(dialog -> {
                                    finish();
                                }).show();
                    }
                });
    }


    public void checkVersion(HttpResult<VersionBean> result) {
        if (result != null) {
            new VersionUpdateDialog(this, result.getData()).show();
        }
    }
}
