package com.ooteco.model.ext;

import com.ooteco.model.UserDelegateTrade;
import com.ooteco.model.UserKnockdownTrade;

import java.util.List;

public class UserDelegateTradeExt extends UserDelegateTrade {

    private List<UserKnockdownTrade> knockdownTrades;

    public List<UserKnockdownTrade> getKnockdownTrades() {
        return knockdownTrades;
    }

    public void setKnockdownTrades(List<UserKnockdownTrade> knockdownTrades) {
        this.knockdownTrades = knockdownTrades;
    }
}
