package com.ooteco.websocket;

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
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/market/{symbol}", configurator = virtualWebSocketConfig.class)
@Component
public class MarketWebSocket {

    @Autowired
    MarketInfoServiceImpl marketInfoService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CopyOnWriteArraySet<MarketWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;
    private ExecutorService exexutors = Executors.newFixedThreadPool(32);

    @OnOpen
    public void onOpen(@PathParam("symbol")String symbol, Session session) throws IOException {
        this.session = session;
        webSocketSet.add(this);
        for (MarketWebSocket item : webSocketSet) {

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        while (item.session.isOpen()) {
                            item.sendMessage(marketInfoService.getMarketInfo(symbol).toString());
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exexutors.execute(r);
        }
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);

    }

    @OnMessage
    public void onMessage(String symbol) throws IOException {

        String s = marketInfoService.getMarketInfo(symbol).toString();
        this.sendMessage(s);

    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
