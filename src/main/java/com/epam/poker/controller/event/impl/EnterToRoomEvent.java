package com.epam.poker.controller.event.impl;

import com.epam.poker.util.constant.Attribute;
import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnterToRoomEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final EnterToRoomEvent instance = new EnterToRoomEvent();
    private static Lobby lobby = Lobby.getInstance();

    private EnterToRoomEvent() {
    }

    public static EnterToRoomEvent getInstance() {
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
        long numberRoom = -1;
        try {
            numberRoom = Long.parseLong(json.get(Attribute.DATA).asText());
        } catch (NumberFormatException e) {
            LOGGER.error("Parse table id illegal: " + e);
        }
        if (gambler.getTitleRoom() == null && numberRoom > -1) {
            String nameRoom = String.format(Attribute.TABLE_WITH_HYPHEN, numberRoom);
            lobby.addGamblerToRoom(nameRoom, gambler);
        }
    }
}
