package com.sskj.common.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sskj.common.R;
import com.sskj.common.R2;
import com.sskj.common.adapter.BaseAdapter;
import com.sskj.common.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCoinDialog extends BottomSheetDialog {

    @BindView(R2.id.coin_list)
    RecyclerView coinList;


    private BaseAdapter<Coin> coinAdapter;

    private List<Coin> coins = new ArrayList<>();

    private OnSelectListener onSelectListener;

    private boolean haveAll;


    public SelectCoinDialog(@NonNull Context context, OnSelectListener onSelectListener) {
        super(context);
        setContentView(R.layout.common_dialog_coin);
        ButterKnife.bind(this);
        this.onSelectListener = onSelectListener;
        initView();
    }

    public SelectCoinDialog(@NonNull Context context, OnSelectListener onSelectListener,Boolean haveAll) {
        super(context);
        setContentView(R.layout.common_dialog_coin);
        ButterKnife.bind(this);
        this.haveAll=haveAll;
        this.onSelectListener = onSelectListener;
        initView();
    }


    private void initView() {
        coinList.setLayoutManager(new LinearLayoutManager(getContext()));
        coinAdapter = new BaseAdapter<Coin>(R.layout.common_item_coind, null, coinList) {
            @Override
            public void bind(ViewHolder holder, Coin item) {
                holder.setText(R.id.name, item.getName());
                holder.itemView.setOnClickListener(view -> {
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(SelectCoinDialog.this, item);
                    }
                });
            }
        };
        if (haveAll){
            coins.add(new Coin("全部"));
        }
        coins.add(new Coin("ETH"));
        coins.add(new Coin("BTC"));
        coins.add(new Coin("EOS"));
        coins.add(new Coin("USDT"));
        coinAdapter.setNewData(coins);
    }


    public interface OnSelectListener {
        void onSelect(SelectCoinDialog dialog, Coin coin);
    }

}
