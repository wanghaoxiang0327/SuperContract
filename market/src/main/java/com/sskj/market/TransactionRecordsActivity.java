package com.sskj.market;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.sskj.common.base.BaseActivity;
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
public class TransactionRecordsActivity extends BaseActivity<TransactionRecordsPresenter> {


    @BindView(R2.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R2.id.content)
    FrameLayout content;

    ArrayList<CustomTabEntity> tabs=new ArrayList<>();
    ArrayList<Fragment> fragments=new ArrayList<>();

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
        tabs.add(new TabItem("持仓"));
        tabs.add(new TabItem("成交"));
        fragments.add(HoldFragment.newInstance());
        fragments.add(DealFragment.newInstance());
        tabLayout.setTabData(tabs,getSupportFragmentManager(),R.id.content,fragments);
    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TransactionRecordsActivity.class);
        context.startActivity(intent);
    }


}
