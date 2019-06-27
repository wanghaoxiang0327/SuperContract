package com.sskj.common.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shizhefei.mvc.IDataAdapter;
import com.sskj.common.R;

import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, ViewHolder> implements IDataAdapter<List<T>> {

    public BaseAdapter(int layoutResId, @Nullable List<T> data, RecyclerView recyclerView) {
        super(layoutResId, data);
        if (recyclerView != null) {
            this.bindToRecyclerView(recyclerView);
        }
    }

    public BaseAdapter(int layoutResId, @Nullable List<T> data, RecyclerView recyclerView,boolean showEmpty) {
        this(layoutResId,data,recyclerView);
        if (showEmpty){
            setEmptyView(R.layout.common_empty_view,null);
        }
    }

    @Override
    protected void convert(ViewHolder helper, T item) {
        bind(helper, item);
    }

    public abstract void bind(ViewHolder holder, T item);

    @Override
    public void setNewData(@Nullable List<T> data) {
        super.setNewData(data);
    }

    @Override
    public void addData(Collection<? extends T> newData) {
        if (newData!=null){
            super.addData(newData);
        }
    }



    @Override
    public void notifyDataChanged(List<T> data, boolean isRefresh) {
        if (isRefresh){
            setNewData(data);
        }else {
            addData(data);
        }
    }

    @Override
    public boolean isEmpty() {
        return  mData==null|| mData.isEmpty();
    }
}
