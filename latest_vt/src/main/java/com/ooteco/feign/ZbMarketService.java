package com.ooteco.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ooteco.controller.AppCustomController;
import com.ooteco.model.ext.ZbMarketInfoResp;
import com.ooteco.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ZbMarketService {
    private static Logger log = Logger.getLogger(ZbMarketService.class);


    public ZbMarketInfoResp get(String market, String type, String since, int size){
        ZbMarketClient zbMarketClient = ClientFactory.createZbMarketClient();
        log.info(type);
        try {
            String result = zbMarketClient.get(market, type, since, size);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            log.info(result);
            JSONObject json = JSON.parseObject(result);
            if (json.get("data") == null || StringUtil.isEmpty(json.get("data").toString())) {
                return null;
            } else {
                ZbMarketInfoResp zbMarketInfoResp = JSONObject.toJavaObject(json,ZbMarketInfoResp.class);

                return zbMarketInfoResp;
            }
        }catch(Exception e){
            get( market, type, since, size);
        }
        return null;

    }


    //http://api.zb.com/data/v1/kline?market=zb_qc&type=1min&size=1
    public static void main(String[] args){
        ZbMarketInfoResp r = new ZbMarketService().get("zb_qc","1min","1520307420000",1);
        System.out.println("=======================");
        System.out.println(r.toString());
        System.out.println("=======================");
    }
}
