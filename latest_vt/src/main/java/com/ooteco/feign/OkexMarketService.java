package com.ooteco.feign;


import com.ooteco.model.ext.OkexMarketInfoResp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OkexMarketService {
    private static Logger log = Logger.getLogger(OkexMarketService.class);

    public String get(String market, String type, String since, int size){
        log.info("============"+market+"============"+type+"============");
        OkexMarketClient okexMarketClient = ClientFactory.createOkexMarketClient();
        String result = okexMarketClient.get(market,type,since,size);
        log.info(result);
        return result;


    }

    public static void main(String[] args){
        String result = new OkexMarketService().get("ltc_btc", "1min", "1520306732155", 1440);
        result = result.replaceAll("\"","");
        System.out.println("===================="+result);
    }
}
