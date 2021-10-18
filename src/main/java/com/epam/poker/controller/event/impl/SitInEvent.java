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

public class SitInEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Lobby lobby = Lobby.getInstance();
    private static final SitInEvent instance = new SitInEvent();
    private static final EventHandlerService eventHandlerService = EventHandlerService.getInstance();

    private SitInEvent() {
    }

    public static SitInEvent getInstance() {
        return instance;
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonLine);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        ObjectNode response = mapper.createObjectNode();
        response.putPOJO(EVENT, json.get(EVENT));
        ObjectNode objectNode = mapper.createObjectNode();
        if (gambler.getSittingOnTable() > -1 && !gambler.isSittingIn()
                && gambler.getNumberSeatOnTable() > -1
                && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
            Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
            objectNode.put(SUCCESS, true);
            sendEvent(gambler, response, objectNode);
            eventHandlerService.gamblerSatIn(table, gambler);
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
}
