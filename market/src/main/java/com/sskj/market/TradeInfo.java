package com.sskj.market;

import com.sskj.market.data.CoinAsset;
import com.sskj.market.data.TradeCoin;

import java.util.List;

public class TradeInfo {

    private List<CoinAsset> coinAssets;

    private List<TradeCoin> tradeCoins;

    public List<CoinAsset> getCoinAssets() {
        return coinAssets;
    }

    public void setCoinAssets(List<CoinAsset> coinAssets) {
        this.coinAssets = coinAssets;
    }

    public List<TradeCoin> getTradeCoins() {
        return tradeCoins;
    }

    public void setTradeCoins(List<TradeCoin> tradeCoins) {
        this.tradeCoins = tradeCoins;
    }
}
