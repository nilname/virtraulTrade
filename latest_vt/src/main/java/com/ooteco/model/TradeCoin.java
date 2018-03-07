package com.ooteco.model;

public class TradeCoin {

    public TradeCoin(){

    }

    public TradeCoin(String tradeCoin){
        this.tradeCoin = tradeCoin;
    }

    public TradeCoin(String baseCoin, String tradeCoin){
        this.baseCoin = baseCoin;
        this.tradeCoin = tradeCoin;
    }

    private String tradeCoin;
    private String baseCoin;

    public String getTradeCoin() {
        return tradeCoin;
    }

    public void setTradeCoin(String tradeCoin) {
        this.tradeCoin = tradeCoin;
    }

    public String getBaseCoin() {
        return baseCoin;
    }

    public void setBaseCoin(String baseCoin) {
        this.baseCoin = baseCoin;
    }

    @Override
    public String toString(){
        return tradeCoin.toUpperCase() + "/" + baseCoin.toUpperCase();
    }
}
