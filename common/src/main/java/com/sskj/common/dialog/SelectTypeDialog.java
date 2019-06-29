package com.sskj.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sskj.common.R;
import com.sskj.common.R2;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;
import com.sskj.common.data.AssetType;
import com.sskj.common.data.CoinAsset;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectTypeDialog extends BottomSheetDialog {

    @BindView(R2.id.coin_list)
    RecyclerView coinList;


    private BaseAdapter<AssetType> coinAdapter;


    private OnSelectListener onSelectListener;



    public SelectTypeDialog(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context);
        setContentView(R.layout.common_dialog_coin);
        ButterKnife.bind(this);
        this.onSelectListener = onSelectListener;
        initView();
    }




    private void initView() {
        coinList.setLayoutManager(new LinearLayoutManager(getContext()));
        coinAdapter = new BaseAdapter<AssetType>(R.layout.common_item_coind, null, coinList) {
            @Override
            public void bind(ViewHolder holder, AssetType item) {
                holder.setText(R.id.name, item.getTitle());
                holder.itemView.setOnClickListener(view -> {
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(SelectTypeDialog.this, item);
                    }
                });
            }
        };
    }


    public void setData(List<AssetType> data){
        coinAdapter.setNewData(data);
    }

    public interface OnSelectListener {
        void onSelect(SelectTypeDialog dialog, AssetType coin);
    }

}
