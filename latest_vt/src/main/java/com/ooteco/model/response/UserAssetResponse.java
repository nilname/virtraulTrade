package com.ooteco.model.response;

import com.ooteco.model.UserCoin;

import java.math.BigDecimal;
import java.util.List;

public class UserAssetResponse {

    private BigDecimal asset;
    private BigDecimal profit;
    private BigDecimal differAmount;
    private List<UserCoin> list;

    public BigDecimal getAsset() {
        return asset;
    }

    public void setAsset(BigDecimal asset) {
        this.asset = asset;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public List<UserCoin> getList() {
        return list;
    }

    public void setList(List<UserCoin> list) {
        this.list = list;
    }

    public BigDecimal getDifferAmount() {
        return differAmount;
    }

    public void setDifferAmount(BigDecimal differAmount) {
        this.differAmount = differAmount;
    }
}
