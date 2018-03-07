package com.ooteco.feign;

import feign.Param;
import feign.RequestLine;

public interface BinanceMarketClient {

    @RequestLine("GET /api/v1/klines?symbol={symbol}&interval={interval}&limit={limit}&startTime={startTime}&endTime={endTime}")
    String get(@Param("symbol") String symbol, @Param("interval")String interval, @Param("limit")int limit , @Param("startTime")String startTime , @Param("endTime")String endTime);
}
