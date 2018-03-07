package com.ooteco.constant;

public enum MarketType {
    huobi("huobi"),
    zb("zb"),
    bian("bian"),
    okex("okex");

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    MarketType(String key) {
        this.key = key;
    }

    public String key;
}
