package com.ooteco.model.ext;

import com.ooteco.model.UserKnockdownTrade;

import java.math.BigDecimal;
import java.util.Date;

public class UserKnockdownTradeExt extends UserKnockdownTrade {

    private BigDecimal averagePrice;
    private Date reverseTime;
    private BigDecimal delegatePrice;
    private BigDecimal delegateQuantity;
    private BigDecimal totalNumber;

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Date getReverseTime() {
        return reverseTime;
    }

    public void setReverseTime(Date reverseTime) {
        this.reverseTime = reverseTime;
    }

    public BigDecimal getDelegatePrice() {
        return delegatePrice;
    }

    public void setDelegatePrice(BigDecimal delegatePrice) {
        this.delegatePrice = delegatePrice;
    }

    public BigDecimal getDelegateQuantity() {
        return delegateQuantity;
    }

    public void setDelegateQuantity(BigDecimal delegateQuantity) {
        this.delegateQuantity = delegateQuantity;
    }

    public BigDecimal getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(BigDecimal totalNumber) {
        this.totalNumber = totalNumber;
    }
}
