package com.ooteco.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ooteco.model.DepthInfo;
import com.ooteco.service.impl.MarketInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/depth/{platform}/{symbol}", configurator = virtualWebSocketConfig.class)
@Component
public class MarketDepthWebSocket {
    private String symbol = "";
    private String platform = "";
    @Autowired
    MarketInfoServiceImpl marketInfoService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CopyOnWriteArraySet<MarketDepthWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;
    private ExecutorService exexutors = Executors.newFixedThreadPool(32);

    @OnOpen
    public void onOpen(@PathParam("platform") String platform, @PathParam("symbol") String symbol, Session session) throws IOException {
        this.symbol = symbol;
        this.platform = platform;
        this.session = session;
        webSocketSet.add(this);
        for (MarketDepthWebSocket item : webSocketSet) {
            try {
                while (item.session.isOpen()) {
//                            JSONArray jsonObj = (JSONArray) JSON.toJSON(marketInfoService.getDepthInfo(platform, symbol));
                    item.sendMessage(getMessages(marketInfoService.getDepthInfo(platform, symbol)));
//                            item.sendMessage(symbol);
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Runnable r = new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            };
//            exexutors.execute(r);
        }
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        this.sendMessage(getMessages(marketInfoService.getDepthInfo(this.platform, this.symbol)));
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private String getMessages(List<?> depthInfoList) {
        return ((JSONArray) JSON.toJSON(depthInfoList)).toJSONString();
    }

}
