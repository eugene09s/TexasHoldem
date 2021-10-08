package com.epam.poker.controller;

import com.epam.poker.controller.chat.Message;
import com.epam.poker.controller.chat.MessageDecoder;
import com.epam.poker.controller.chat.MessageEncoder;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.EndpointAwareConfig;
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
        configurator = EndpointAwareConfig.class)
public class ChatController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LINE_COOKIE = "cookie";
    private static Set<Session> sessionUsers = Collections.synchronizedSet(new HashSet<>());
    private static JwtProvider jwtProvider = JwtProvider.getInstance();
    private String username;
    private String img;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        String token = null;
        Object cookies = config.getUserProperties().get(LINE_COOKIE);
        token = parseToken(cookies);
        if (null != token && jwtProvider.validateToken(token)) {
            Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
            this.username = claimsJws.getBody().get(Attribute.LOGIN).toString();
            ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
            try {
                this.img = profilePlayerService.findProfilePlayerById(
                        Long.parseLong(claimsJws.getBody().get(Attribute.USER_ID).toString())).getPhoto();
            } catch (ServiceException e) {
                LOGGER.error("User not found with name: " + username + " Error: " + e);
            }
            sessionUsers.add(session);
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
    public void onMessage(Message message) {
        message.setName(this.username);
        message.setImg(this.img);
        String time = LocalTime.now().toString();
        message.setTime(time.substring(0, time.length() - 7));
        message.setText(checkerHtmlInjectionMessage(message.getText()));
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

    private String checkerHtmlInjectionMessage(String line) {
        return line.replace("$", "Dollars")
                .replace("<", "&lt;")
                .replace(">", "&rt;")
                .replace("}", "circle bracket")
                .replace("{", "circle bracket");
    }
}
