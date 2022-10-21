package com.example.ws.tomcat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@Component
@ServerEndpoint("/ws/{dataid}")
public class WebSocketServer{
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;
    /**
     * 标识当前连接客户端的 dataid
     */
    private String dataid;
    /**
     * 当前 uname
     */
    private String uname;


    @OnOpen
    public void open(Session session, @PathParam("dataid") String dataid) throws IOException {

        this.dataid=dataid;
        logger.info("open dataid: {}",dataid);
    }


    @OnClose
    public void onClose() {
        logger.info("close dataid: {}",dataid);
    }


    @OnMessage
    public void onMessage(String message) throws IOException {
        logger.info("dataid: {},message:{}",dataid,message);
        this.session.getBasicRemote().sendText("hello,I received "+message);
    }



}
