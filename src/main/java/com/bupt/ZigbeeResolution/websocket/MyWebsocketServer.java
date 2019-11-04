package com.bupt.ZigbeeResolution.websocket;


import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ：HappyCheng
 * @date ：2019/10/6
 */
@ServerEndpoint("/ws")
@Component
public class MyWebsocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，
    // 可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArrayList<MyWebsocketServer> webSocketSet = new CopyOnWriteArrayList<MyWebsocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    /**
     * 连接建立成功时调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);//加入set中
        addOnlineCount();  //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        webSocketSet.remove(this);//
        subOnlineCount();  //在线人数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("来自客户端的消息: " + message);
        //群发消息
        sendAllMessage(message);
    }

    /**
     * 发生错误时调用的地方
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public static void sendAllMessage(String message) throws IOException {
        for (MyWebsocketServer item : webSocketSet) {
            try {
                item.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized void addOnlineCount() {
        MyWebsocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebsocketServer.onlineCount--;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

}
