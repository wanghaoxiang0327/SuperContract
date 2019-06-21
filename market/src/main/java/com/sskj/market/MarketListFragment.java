package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.DataSource;
import com.sskj.common.adapter.SmartRefreshHelper;
import com.sskj.common.base.BaseFragment;

import org.reactivestreams.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.Flowable;

/**
 * @author Hey
 * Create at  2019/05/29
 */
public class MarketListFragment extends BaseFragment<MarketListPresenter> {


    @BindView(R2.id.market_list)
    RecyclerView marketList;
    @BindView(R2.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    BaseAdapter<String> adapter;
    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_market_list;
    }

    @Override
    public MarketListPresenter getPresenter() {
        return new MarketListPresenter();
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {
        SmartRefreshHelper<List<String>> smartHelper=new SmartRefreshHelper<>(smartRefresh);
        smartHelper.setDataSource(new DataSource<String>() {
            @Override
            public Flowable<List<String>> bindData(int page) {
                return null;
            }
        });
        smartHelper.setAdapter(adapter);
    }

    @Override
    public void loadData() {

    }


    public static MarketListFragment newInstance() {
        MarketListFragment fragment = new MarketListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }



}
