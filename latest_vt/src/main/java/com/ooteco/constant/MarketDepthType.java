package com.ooteco.constant;

public enum  MarketDepthType {

    unknown,asks,bids;

    public static boolean isAsksType(Integer ordinal){
        return asks.ordinal() == ordinal;
    }

    public static boolean isBidsType(Integer ordinal){
        return bids.ordinal() == ordinal;
    }
}
