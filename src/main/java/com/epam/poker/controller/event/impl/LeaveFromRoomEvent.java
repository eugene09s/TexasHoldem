package com.epam.poker.controller.event.impl;

import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeaveFromRoomEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final LeaveFromRoomEvent instance = new LeaveFromRoomEvent();
    private static final Lobby lobby = Lobby.getInstance();

    private LeaveFromRoomEvent() {
    }

    public static LeaveFromRoomEvent getInstance() {
        return instance;
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        if (gambler.getTitleRoom() != null && gambler.getSittingOnTable() < 0) {
            String titleRoom = gambler.getTitleRoom();
            gambler.leaveTable();
            lobby.findRoom(titleRoom).deleteGambler(gambler);
        } else {
            LOGGER.warn("Gamler name: " + gambler.getName() + " is not room");
        }
    }
}
