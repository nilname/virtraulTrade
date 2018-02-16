package com.ooteco.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint("/market/RealTimeInfo")
@Component
public class MarketWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<MarketWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;
    private ExecutorService exexutors= Executors.newFixedThreadPool(4);

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        webSocketSet.add(this);
        incrOnlineCount();
        for (MarketWebSocket item : webSocketSet) {
            if (!item.equals(this)) { //send to others only.
                item.sendMessage("someone just joined in.");
            }
            Runnable r = new Runnable() {
                @Override
                public void run() {
//                    Todo 处理实际业务逻辑
                }
            };
            exexutors.execute(r);
//            item.sendMessage("sending information");
        }
        logger.info("new connection...current online count: {}", getOnlineCount());
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);
        decOnlineCount();
        for (MarketWebSocket item : webSocketSet) {
            item.sendMessage("someone just closed a connection.");
        }
        logger.info("one connection closed...current online count: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("message received: {}", message);
        // broadcast received message
        for (MarketWebSocket item : webSocketSet) {
            System.out.println(message);
            String s = "server receved :" + message;
            item.sendMessage(s);
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return MarketWebSocket.onlineCount;
    }

    public static synchronized void incrOnlineCount() {
        MarketWebSocket.onlineCount++;
    }

    public static synchronized void decOnlineCount() {
        MarketWebSocket.onlineCount--;
    }
}
