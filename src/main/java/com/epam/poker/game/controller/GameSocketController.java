package com.epam.poker.game.controller;

import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.game.controller.command.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.lobby.Lobby;
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
        String eventName = null;
        String jsonLineData = null;
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readValue(message, JsonNode.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        eventName = jsonNode.get(Parameter.EVENT).asText();
        jsonLineData = jsonNode.get(Parameter.DATA).asText();
        if (eventName != null && jsonLineData != null) {
            EventSocket eventSocket = EventSocket.of(eventName);
            eventSocket.execute(jsonLineData, this.gambler);
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
        lobby.deleteGambler(this.gambler.getName(), gambler);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("Websocket error with user id:" + session + "\n\n" + throwable);
    }
}
