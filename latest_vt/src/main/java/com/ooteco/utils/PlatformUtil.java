package com.ooteco.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ooteco.exceptions.CustomException;
import com.ooteco.model.TradeCoin;

import java.math.BigDecimal;
import java.util.Optional;

public class PlatformUtil {

    private final static String defaultBaseCoinName = "usdt";

    public enum Type{
        zb("http://api.zb.com/data/v1/ticker?market="),
        huobi(""),
        bian("https://api.binance.com/api/v3/ticker/price?symbol="),
        okex("");

        private String marketPriceUrl;

        Type(String marketPriceUrl){
            this.marketPriceUrl = marketPriceUrl;
        }

    }

    public static BigDecimal getMarketPrice(TradeCoin tradeCoin) throws CustomException{
        if(tradeCoin.getBaseCoin() == null){
            tradeCoin.setBaseCoin(defaultBaseCoinName);
        }
        BigDecimal price;
        price = getZBMarketPrice(tradeCoin);
            if(isGetMarketPrice(price)){
                return price;
            }
        price = getBIANMarketPrice(tradeCoin);
        if(isGetMarketPrice(price)){
            return price;
        }
        throw new CustomException(tradeCoin.toString() + "未找到市场价");
    }

    public static BigDecimal getBIANMarketPrice(TradeCoin tradeCoin){
        String marketPriceUrl = Type.bian.marketPriceUrl + tradeCoin.getTradeCoin().toUpperCase()
                + tradeCoin.getBaseCoin().toUpperCase();
        String result = HttpRequestUtil.sendGetRequest(marketPriceUrl);
        Optional<JSONObject> jsonObject = Optional.ofNullable(JSON.parseObject(result));
        String price = jsonObject.map(o -> o.getString("price")).orElse("0");
        return new BigDecimal(price);
    }

    public static BigDecimal getZBMarketPrice(TradeCoin tradeCoin){
        String marketPriceUrl = Type.zb.marketPriceUrl + tradeCoin.getTradeCoin() + "_"
                + tradeCoin.getBaseCoin();
        String result = HttpRequestUtil.sendGetRequest(marketPriceUrl);
        Optional<JSONObject> jsonObject = Optional.ofNullable(JSON.parseObject(result));
        String price = jsonObject.map(o -> o.getJSONObject("ticker")).map(o -> o.getString("last")).orElse("0");
        return new BigDecimal(price);
    }

    private static boolean isGetMarketPrice(BigDecimal price){
        if(price.compareTo(new BigDecimal(0)) > 0){
            return true;
        }
        return false;
    }
}
