package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeaveFromRoomEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final LeaveFromRoomEvent instance = new LeaveFromRoomEvent();
    private static Lobby lobby = Lobby.getInstance();

    private LeaveFromRoomEvent() {
    }

    public static LeaveFromRoomEvent getInstance() {
        return instance;
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
//        JsonNode json = mapper.createObjectNode();
//        JsonNode jsonData = json.get(Attribute.DATA);
        if (gambler.getTitleRoom() != null && !gambler.isSittingOnTable()) {
            String titleRoom = gambler.getTitleRoom();
            lobby.findRoom(titleRoom).deleteGambler(gambler);
        } else {
            LOGGER.warn("Gamler name: " + gambler.getName() + " is not room");
        }
    }
}
