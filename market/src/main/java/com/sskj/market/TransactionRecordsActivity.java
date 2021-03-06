package com.sskj.market;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.sskj.common.base.BaseActivity;
import com.sskj.common.router.RoutePath;
import com.sskj.common.tab.TabItem;
import com.sskj.common.tab.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 交易记录
 *
 * @author Hey
 * Create at  2019/06/26
 */
@Route(path = RoutePath.ORDER_MANAGER)
public class TransactionRecordsActivity extends BaseActivity<TransactionRecordsPresenter> {


    @BindView(R2.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R2.id.left_img)
    ImageView leftImg;

    @BindView(R2.id.content)
    FrameLayout content;

    ArrayList<CustomTabEntity> tabs = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.market_activity_transaction_records;
    }

    @Override
    public TransactionRecordsPresenter getPresenter() {
        return new TransactionRecordsPresenter();
    }

    @Override
    public void initView() {
        leftImg.setOnClickListener(v -> {
            finish();
        });
        tabs.add(new TabItem(getString(R.string.market_hold)));
        tabs.add(new TabItem(getString(R.string.market_deal)));
        fragments.add(HoldFragment.newInstance());
        fragments.add(DealFragment.newInstance());
        tabLayout.setTabData(tabs, getSupportFragmentManager(), R.id.content, fragments);
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TransactionRecordsActivity.class);
        context.startActivity(intent);
    }


}
