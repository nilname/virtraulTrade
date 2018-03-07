package com.ooteco.feign;


import feign.Param;
import feign.RequestLine;

public interface ZbMarketClient {
    @RequestLine("GET /data/v1/kline?market={market}&type={type}&since={since}&size={size}")
    String get(@Param("market") String market,@Param("type")String type,@Param("since")String since,@Param("size")int size );


}
