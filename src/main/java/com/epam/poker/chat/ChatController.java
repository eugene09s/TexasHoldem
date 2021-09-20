package com.epam.poker.chat;

import com.epam.poker.controller.command.constant.Attribute;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/chat",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class},
        configurator = ServletAwareConfig.class)
public class ChatController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Set<Session> sessionUsers = Collections.synchronizedSet(new HashSet<>());
    private Session session = null;
    private String username = "anonimus";
    private HttpSession pastHttpSession;
    private String img = "notAva.jpg";

    @OnOpen
    public void onOpen(Session session,  EndpointConfig config) {
        this.session = session;
        this.pastHttpSession = (HttpSession) config.getUserProperties().get("httpSession");
        this.username = String.valueOf(pastHttpSession.getAttribute(Attribute.LOGIN));
        this.img = String.valueOf(pastHttpSession.getAttribute(Attribute.PHOTO));
        sessionUsers.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessionUsers.remove(session);
    }

    @OnError
    public void  onError(Session session, Throwable throwable) {
        LOGGER.error("Websocket error with user id:" + session + "\n\n" + throwable);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        message.setName(this.username);
        message.setImg(this.img);
        sessionUsers.forEach(s->{
            try {
                s.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                LOGGER.error(e);
            }
        });
    }

}
