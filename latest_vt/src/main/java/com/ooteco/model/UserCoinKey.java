package com.ooteco.model;

import java.io.Serializable;

public class UserCoinKey implements Serializable {
    private Integer userId;

    private String coin;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin == null ? null : coin.trim();
    }
}