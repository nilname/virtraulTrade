package com.ooteco.feign;

import feign.Param;
import feign.RequestLine;

public interface OkexMarketClient {

    @RequestLine("GET /api/v1/kline.do?symbol={symbol}&type={type}&size={size}&since={since}")
    String get(@Param("symbol") String symbol, @Param("type")String type ,@Param("since")String since, @Param("size")int size);
}
