package com.ooteco.model;

import java.io.Serializable;

public class UserKnockdownTradeKey implements Serializable {
    private Integer askTradeId;

    private Integer bidTradeId;

    private static final long serialVersionUID = 1L;

    public Integer getAskTradeId() {
        return askTradeId;
    }

    public void setAskTradeId(Integer askTradeId) {
        this.askTradeId = askTradeId;
    }

    public Integer getBidTradeId() {
        return bidTradeId;
    }

    public void setBidTradeId(Integer bidTradeId) {
        this.bidTradeId = bidTradeId;
    }
}