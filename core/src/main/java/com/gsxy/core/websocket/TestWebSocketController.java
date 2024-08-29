package com.gsxy.core.websocket;

import com.gsxy.core.controller.SignInController;
import com.gsxy.core.service.impl.SignInServiceImpl;
import com.gsxy.core.util.SpringContextUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{id}")
@CrossOrigin
@Component
public class TestWebSocketController {

    // 依赖注入
    private SignInController signInController = SpringContextUtil.getBean(SignInController.class);
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Long id, Session session) throws IOException {
        String s = this.serviceFunction(id, session);
        session.getBasicRemote().sendText(s);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("WebSocket closed for session: " + session.getId());
    }

    public String serviceFunction(Long id, Session session) throws IOException {
        return signInController.querySignInUserBySignInId(id);
    }

    private static void broadcastMessage(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

