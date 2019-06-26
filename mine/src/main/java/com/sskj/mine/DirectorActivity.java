package com.sskj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseActivity;
import com.sskj.mine.data.DividendBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 董事分红
 *
 * @author Hey
 * Create at  2019/06/25
 */
public class DirectorActivity extends BaseActivity<DirectorPresenter> {

    @BindView(R2.id.btc_total)
    TextView btcTotal;
    @BindView(R2.id.eth_total)
    TextView ethTotal;
    @BindView(R2.id.eos_total)
    TextView eosTotal;
    @BindView(R2.id.usdt_total)
    TextView usdtTotal;
    @BindView(R2.id.btc_last)
    TextView btcLast;
    @BindView(R2.id.eth_last)
    TextView ethLast;
    @BindView(R2.id.eos_last)
    TextView eosLast;
    @BindView(R2.id.usdt_last)
    TextView usdtLast;
    @BindView(R2.id.dividend_list)
    RecyclerView dividendList;


    BaseAdapter<DividendBean> adapter;


    @Override
    public int getLayoutId() {
        return R.layout.mine_activity_director;
    }

    @Override
    public DirectorPresenter getPresenter() {
        return new DirectorPresenter();
    }

    @Override
    public void initView() {

        dividendList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter<DividendBean>(R.layout.mine_item_dividend, null, dividendList) {
            @Override
            public void bind(ViewHolder holder, DividendBean item) {

            }
        };

    }

    @Override
    public void initData() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DirectorActivity.class);
        context.startActivity(intent);
    }


}
