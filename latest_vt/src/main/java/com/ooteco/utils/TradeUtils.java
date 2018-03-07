package com.ooteco.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ooteco.constant.MarketDepthType;
import com.ooteco.exceptions.SymbolParseException;
import com.ooteco.model.MarketDepth;
import com.ooteco.model.TradeCoin;

import java.math.BigDecimal;

public class TradeUtils {

    public static TradeCoin getTradeCoin(String symbol) throws SymbolParseException{
        TradeCoin tradeCoin = new TradeCoin();
        String[] coins = symbol.split("_");
        if(coins.length != 2){
            throw new SymbolParseException(symbol);
        }
        tradeCoin.setTradeCoin(coins[0]);
        tradeCoin.setBaseCoin(coins[1]);
        return tradeCoin;
    }

    public static MarketDepthType getMarketDepthType(Integer ordinal){
        for(MarketDepthType marketDepthType : MarketDepthType.values()){
            if(ordinal == marketDepthType.ordinal()){
                return marketDepthType;
            }
        }
        return MarketDepthType.unknown;
    }

    public static BigDecimal getCoinUsdtPrice(String platform, String coin){
//        url = url + "?symbol=" + coin.toUpperCase() + "USDT";
        String result = HttpRequestUtil.sendGetRequest("");
        JSONObject json = JSON.parseObject(result);
        return new BigDecimal(json.getString("price"));
    }
}
