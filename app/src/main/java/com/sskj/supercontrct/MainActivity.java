package com.sskj.supercontrct;

import android.os.Bundle;
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
import com.sskj.common.dialog.TipDialog;
import com.sskj.common.exception.LogoutException;
import com.sskj.common.http.BaseHttpConfig;
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
    public void initView() {
        mainTabs.add(new TabItem("首页", R.mipmap.tab_home, R.mipmap.tab_home_p));
        mainTabs.add(new TabItem("资讯", R.mipmap.tab_news, R.mipmap.tab_news_p));
        mainTabs.add(new TabItem("资产", R.mipmap.tab_asset, R.mipmap.tab_asset_p));
        mainTabs.add(new TabItem("我的", R.mipmap.tab_mine, R.mipmap.tab_mine_p));
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
    protected void onDestroy() {
        super.onDestroy();
        marketWebSocket.closeWebSocket();
    }
}
