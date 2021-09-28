package com.epam.poker.controller.chat;

import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.util.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalTime;
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
    private static JwtProvider jwtProvider = JwtProvider.getInstance();
    private Session session;
    private String username;
    private String token;
    private String img;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        Object cookies = config.getUserProperties().get("cookie");
        this.token = parseToken(cookies);
        if (null != this.token){
            if (jwtProvider.validateToken(this.token)) {
                Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
                this.username = claimsJws.getBody().get(Attribute.LOGIN).toString();
                this.img = claimsJws.getBody().get(Attribute.PHOTO).toString();
                sessionUsers.add(session);
            }
        } else {
            onClose(session);
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessionUsers.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("Websocket error with user id:" + session + "\n\n" + throwable);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        message.setName(this.username);
        message.setImg(this.img);
        String time = LocalTime.now().toString();
        message.setTime(time.substring(0, time.length() - 7));
        sessionUsers.forEach(s -> {
            try {
                s.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                LOGGER.error(e);
            }
        });
    }

    private String parseToken(Object cookies) {
        String[] keyValueLines = String.valueOf(cookies).split(";");
        for (String kvPair : keyValueLines) {
            String[] kv = kvPair.split("=");
            String key = kv[0].replace("[", "").trim();
            String value = kv[1].replace("]", "");
            if (key.equalsIgnoreCase(Attribute.ACCESS_TOKEN)) {
                return value;
            }
        }
        return null;
    }
}
