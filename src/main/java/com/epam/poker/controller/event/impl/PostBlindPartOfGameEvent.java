package com.epam.poker.controller.event.impl;

import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.game.EventHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.epam.poker.util.constant.Attribute.*;

public class PostBlindPartOfGameEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Lobby lobby = Lobby.getInstance();
    private static final PostBlindPartOfGameEvent instance = new PostBlindPartOfGameEvent();
    private static final EventHandlerService eventHandlerService = EventHandlerService.getInstance();

    private PostBlindPartOfGameEvent() {
    }

    @Override
    public void execute(String jsonRequest, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonRequest);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        boolean postedBlind = json.get(DATA).asBoolean();
        ObjectNode response = mapper.createObjectNode();
        response.putPOJO(EVENT, json.get(EVENT));
        ObjectNode objectNode = mapper.createObjectNode();
        if (gambler.getSittingOnTable() > -1
                && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
            Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
            int activeSeat = table.getActiveSeat();
            if (table.getSeats()[activeSeat] == gambler
                    && (table.getPhaseGame().equals(SMALL_BLIND)
                    || table.getPhaseGame().equals(BIG_BLIND))) {
                if (postedBlind) {
                    objectNode.put(SUCCESS, true);
                    sendEvent(gambler, response, objectNode);
                    if (table.getPhaseGame().equals(SMALL_BLIND)) {
                        eventHandlerService.gamblerPostedSmallBlind(table, gambler);
                    } else {
                        eventHandlerService.gamblerPostedBigBlind(table, gambler);
                    }
                } else {
                    eventHandlerService.gamblerSatOut(table, gambler, false);
                    objectNode.put(SUCCESS, true);
                    sendEvent(gambler, response, objectNode);
                }
            }
        }
    }

    private void sendEvent(Gambler gambler, ObjectNode response, ObjectNode objectNode) {
        response.putPOJO(DATA, objectNode);
        try {
            gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
        } catch (IOException e) {
            LOGGER.error("Send JSON: " + e);
        }
    }

    public static PostBlindPartOfGameEvent getInstance() {
        return instance;
    }
}
