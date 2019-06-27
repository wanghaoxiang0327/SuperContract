package com.sskj.market;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.base.BaseFragment;
import com.sskj.common.http.Page;
import com.sskj.market.data.HoldBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create at  2019/06/26
 */
public class HoldFragment extends BaseFragment<HoldPresenter> {


    BaseAdapter<HoldBean> holdAdapter;
    @BindView(R2.id.records_list)
    RecyclerView recordsList;

    private int page = 1;
    private int size = 10;

    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_hold;
    }

    @Override
    public HoldPresenter getPresenter() {
        return new HoldPresenter();
    }

    @Override
    public void initView() {

        holdAdapter = new BaseAdapter<HoldBean>(R.layout.market_item_hold, null, recordsList) {
            @Override
            public void bind(ViewHolder holder, HoldBean item) {

            }
        };
    }


    @Override
    public void initData() {
        mPresenter.getOrder(1, page, size);
    }

    @Override
    public void loadData() {

    }


    public static HoldFragment newInstance() {
        HoldFragment fragment = new HoldFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setData(Page<HoldBean> data) {
        if (page == 1) {
            holdAdapter.setNewData(data.getRes());
        } else {
            holdAdapter.addData(data.getRes());
        }

    }
}
