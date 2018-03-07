package com.ooteco.feign;

import feign.Feign;
import feign.codec.StringDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public class ClientFactory {

    /**
     * http://api.zb.com/data/v1/kline?market=zb_qc&type=1min&size=1
     */
    public static ZbMarketClient createZbMarketClient() {
        return Feign.builder()
                .logger(new Slf4jLogger(ClientFactory.class.getName()))
                .encoder(new GsonEncoder())
                .decoder(new StringDecoder())
                .target(ZbMarketClient.class, "http://api.zb.com");
    }
    /**
     * http://api.zb.com/data/v1/kline?market=zb_qc&type=1min&size=1
     */
    public static HuobiMarketClient createHuobiMarketClient() {
        return Feign.builder()
                .logger(new Slf4jLogger(ClientFactory.class.getName()))
                .encoder(new GsonEncoder())
                .decoder(new StringDecoder())
                .target(HuobiMarketClient.class, "https://api.huobi.pro");
    }


    public static OkexMarketClient createOkexMarketClient() {
        return Feign.builder()
                .logger(new Slf4jLogger(ClientFactory.class.getName()))
                .encoder(new GsonEncoder())
                .decoder(new StringDecoder())
                .target(OkexMarketClient.class, "https://www.okex.com");
    }

    public static BinanceMarketClient createBinanceMarketClient() {
        return Feign.builder()
                .logger(new Slf4jLogger(ClientFactory.class.getName()))
                .encoder(new GsonEncoder())
                .decoder(new StringDecoder())
                .target(BinanceMarketClient.class, "https://api.binance.com");
    }
}
