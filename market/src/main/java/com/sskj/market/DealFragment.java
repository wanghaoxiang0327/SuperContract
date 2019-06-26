package com.sskj.market;

import com.sskj.common.base.BaseFragment;

import com.sskj.market.R;
import android.os.Bundle;

/**
 *
 * Create at  2019/06/26
 */
public class DealFragment extends BaseFragment<DealPresenter> {


    @Override
    public int getLayoutId() {
        return R.layout.market_fragment_deal;
    }

    @Override
    public DealPresenter getPresenter() {
        return new DealPresenter();
    }

    @Override
    public void initView() {
        
    }


    @Override
    public void initData() {
      
    }

    @Override
    public void loadData() {

    }


    public static DealFragment newInstance() {
        DealFragment fragment = new DealFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

 

}
