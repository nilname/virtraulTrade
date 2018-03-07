package com.ooteco.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ooteco.model.marketMessage;
import com.ooteco.service.impl.MarketInfoServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by fangqing on 2/24/18.
 */
@Controller
@EnableScheduling
public class WebSocketContoller {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    MarketInfoServiceImpl marketInfoService;

    @ApiOperation("websocke 测试页面")
    @GetMapping("/websocket")
    public String index() {
        return "index";
    }

    @ApiOperation(value = "获取市场行情况websocket接口", notes = "获取指定平台的市场行请 订阅/market/send 请求{platform:<platform>} 可选huobi zb okex bian")
    @MessageMapping("/markets")
    @SendTo("/market/send")
    public String markets(marketMessage message) throws Exception {
        return getMessages(marketInfoService.getMarketInfos(message.platform));
    }


    @ApiOperation(value = "获取指定平台指定symbol的市场行情况websocket接口")
    @MessageMapping("/marketinfo")
    @SendTo("/market/send")
    public String marketinfo(marketMessage message) throws Exception {
        return getMessages(marketInfoService.getMarketInfo(message.platform, message.symbol));
    }

    @ApiOperation(value = "获取挂单信息", notes = "订阅/market/send 请求{platform:<>,symbol:<>}")
    @MessageMapping("/depth")
    @SendTo("/market/send")
    public String getdepth(marketMessage message) throws Exception {
        return getMessages(marketInfoService.getDepthInfo(message.platform, message.symbol));
    }


    @ApiOperation(value = "获取指定平台指定symbol的历史交易记录websocket接口")
    @MessageMapping("/tradeHistory")
    @SendTo("/market/send")
    public String tradeHistoryInfo(marketMessage message) throws Exception {
        return getMessages(marketInfoService.getTradeHistory(message.platform, message.symbol));
    }

    @ApiOperation("定时刷新火币行请")
    @Scheduled(fixedRate = 1000)
    @SendTo("/market/callbackhuobi")
    public Object callbackhuobi() throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/market/callbackhuobi", getMessages(marketInfoService.getMarketInfos("huobi")));
        return "callbackhuobi";
    }

    @ApiOperation("定时刷新zb行请")
    @Scheduled(fixedRate = 1000)
    @SendTo("/market/callbackzb")
    public Object callbackzb() throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/market/callbackzb", getMessages(marketInfoService.getMarketInfos("zb")));
        return "callbackzb";
    }

    @ApiOperation("定时刷新币安行请")
    @Scheduled(fixedRate = 1000)
    @SendTo("/market/callbackbian")
    public Object callbackbian() throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/market/callbackbian", getMessages(marketInfoService.getMarketInfos("bian")));
        return "callbackbian";
    }

    @ApiOperation("定时刷新OKEX行请")
    @Scheduled(fixedRate = 1000)
    @SendTo("/market/callbackokex")
    public Object callbackokex() throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/market/callbackokex", getMessages(marketInfoService.getMarketInfos("okex")));
        return "callbackokex";
    }

    @ApiOperation("定时刷新OKEX行请")
    @Scheduled(fixedRate = 1000)
    @SendTo("/market/red")
    public Object getPlatformTradeTypeTradeRecords() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/market/callbackokex", getMessages(marketInfoService.getMarketInfos("okex")));
        return "callbackokex";
    }

    private String getMessages(List<?> marketInfolist) {
        return ((JSONArray) JSON.toJSON(marketInfolist)).toJSONString();
    }
}
