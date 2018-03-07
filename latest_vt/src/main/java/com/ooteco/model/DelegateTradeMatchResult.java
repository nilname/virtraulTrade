package com.ooteco.model;

import java.math.BigDecimal;

public class DelegateTradeMatchResult {

    private Integer status;
    private BigDecimal matchedQuantity;
    private BigDecimal price;
    private BigDecimal matchedAmount;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMatchedQuantity() {
        return matchedQuantity;
    }

    public void setMatchedQuantity(BigDecimal matchedQuantity) {
        this.matchedQuantity = matchedQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMatchedAmount() {
        return getMatchedQuantity().multiply(getPrice());
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
    }
}
