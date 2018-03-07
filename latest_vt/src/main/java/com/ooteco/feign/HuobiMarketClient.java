package com.ooteco.feign;

import feign.Param;
import feign.RequestLine;

public interface HuobiMarketClient {

    @RequestLine("GET /market/history/kline?symbol={symbol}&period={period}&size={size}")
    String get(@Param("symbol") String symbol, @Param("period")String period,@Param("size")int size );
}
