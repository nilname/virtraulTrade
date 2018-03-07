package com.ooteco.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ooteco.model.ext.HuobiMarketInfoResp;
import com.ooteco.model.ext.MarketInfoMain;
import com.ooteco.model.ext.OkexMarketInfoResp;
import com.ooteco.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class HuobiMarketService {

    private static Logger log = Logger.getLogger(HuobiMarketService.class);

    public List<HuobiMarketInfoResp> get(String market, String type, int size){
        HuobiMarketClient huobiMarketClient = ClientFactory.createHuobiMarketClient();
        String result = huobiMarketClient.get(market,type,size);
        log.info(result);
        JSONObject json = JSON.parseObject(result);
        if (json.get("data") == null || StringUtil.isEmpty(json.get("data").toString())) {
            return null;
        } else {
            return JSON.parseArray(json.get("data").toString(), HuobiMarketInfoResp.class);
        }

    }


    public static void main(String[] args){
        List<HuobiMarketInfoResp> list = new HuobiMarketService().get("zilbtc","1min",1440);
        System.out.println(list.toString());
    }


}
