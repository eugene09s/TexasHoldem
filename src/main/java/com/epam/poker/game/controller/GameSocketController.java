package com.epam.poker.game.controller;

import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.Parameter;
import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.util.EndpointAwareConfig;
import com.epam.poker.util.jwt.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(value = "/game-poker",
        configurator = EndpointAwareConfig.class)
public class GameSocketController {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LINE_COOKIE = "cookie";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JwtProvider jwtProvider = JwtProvider.getInstance();
    private static final Lobby lobby = Lobby.getInstance();
    private Gambler gambler;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        Object cookies = config.getUserProperties().get(LINE_COOKIE);
        String token = parseToken(cookies);
        if (null != token && jwtProvider.validateToken(token)) {
            Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
            String username = claimsJws.getBody().get(Attribute.LOGIN).toString();
            String img = claimsJws.getBody().get(Attribute.PHOTO).toString();
            lobby.addGambler(username, gambler);
            gambler = Gambler.builder()
                    .setName(username)
                    .setSession(session)
                    .setImg(img)
                    .setTableId(-1)
                    .createGambler();
        } else {
            onClose();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        String eventName;
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + message + " Error:" + e);
        }
        if (jsonNode.findValue(Parameter.EVENT) != null) {
            eventName = jsonNode.get(Parameter.EVENT).asText();
            EventSocket eventSocket = EventSocket.of(eventName);
            if (eventSocket != null) {
                eventSocket.execute(message, this.gambler);
            } else {
                LOGGER.warn("Event not found: " + eventName);
            }
        }
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

    @OnClose
    public void onClose() {
        disconnectGambler();//todo sometimes out of bounds exept
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("Websocket error with user id:" + session + "\n\n" + throwable);
    }

    private void disconnectGambler() {
        String roomName = gambler.getTitleRoom();
        if (roomName != null) {
            lobby.findRoom(roomName).getTable().deleteGamblerToSeats(gambler);
            lobby.findRoom(roomName).deleteGambler(gambler);
        }
        lobby.deleteGambler(this.gambler.getName());
        this.gambler = null;
    }
}
