package com.ooteco.feign;

import com.ooteco.model.ext.BinanceMarketInfoResp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class BinanceMarketService {

    private static Logger log = Logger.getLogger(BinanceMarketService.class);

    public String get(String market, String type, int size, String startSince, String endSince){
        log.info(market +"=========="+type+"================"+startSince+"================"+endSince);
        BinanceMarketClient binanceMarketClient = ClientFactory.createBinanceMarketClient();
        String result = binanceMarketClient.get(market,type,size,startSince,endSince);
        log.info(result);
        //BinanceMarketInfoResp binanceMarketInfoResp = new BinanceMarketInfoResp();

        return result;


    }

    public static void main(String[] args){
       new BinanceMarketService().get("BNBBTC","1m",500,"1520306732155","1520393132155");
    }
}
