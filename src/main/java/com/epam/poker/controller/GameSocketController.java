package com.epam.poker.controller;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.Parameter;
import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
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
            ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
            String img = "";
            try {
                img = profilePlayerService.findProfilePlayerById(
                        Long.parseLong(claimsJws.getBody().get(Attribute.USER_ID).toString())).getPhoto();
            } catch (ServiceException e) {
                LOGGER.error("User not found with name: " + username + " Error: " + e);
            }
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
        String nameGambler = "not authorization";
        if (gambler != null) {
            nameGambler = gambler.getName();
        }
        LOGGER.error("Websocket error with user: " + nameGambler + ". Error: " + throwable);
    }

    private void disconnectGambler() {
        if (gambler != null) {
            String roomName = gambler.getTitleRoom();
            if (roomName != null) {
//            lobby.findRoom(roomName).getTable().deleteGamblerToSeats(gambler);
                lobby.findRoom(roomName).deleteGambler(gambler);
            }
            lobby.deleteGambler(this.gambler.getName());
            this.gambler = null;
        }
    }


}
