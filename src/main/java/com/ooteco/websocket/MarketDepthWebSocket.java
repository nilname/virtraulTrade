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

@ServerEndpoint(value = "/depth/{symbol}",configurator = virtualWebSocketConfig.class)
@Component
public class MarketDepthWebSocket {
    private String symbol="";
    @Autowired
    MarketInfoServiceImpl marketInfoService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CopyOnWriteArraySet<MarketDepthWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;
    private ExecutorService exexutors = Executors.newFixedThreadPool(32);

    @OnOpen
    public void onOpen(@PathParam("symbol") String symbol, Session session) throws IOException {
        this.symbol=symbol;
        this.session = session;
        webSocketSet.add(this);
        for (MarketDepthWebSocket item : webSocketSet) {

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        while (item.session.isOpen()) {
                            item.sendMessage(marketInfoService.getDepthInfo(symbol).toString());
                            item.sendMessage(symbol);
                            Thread.sleep(1000);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
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
    public void onMessage(String message) throws IOException {
        this.sendMessage(marketInfoService.getDepthInfo(this.symbol).toString());
//        this.sendMessage(symbol);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
