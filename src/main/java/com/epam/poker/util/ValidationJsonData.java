package com.epam.poker.util;

import com.epam.poker.model.entity.game.Gambler;
import com.epam.poker.model.entity.game.Lobby;
import com.epam.poker.util.constant.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidationJsonData {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lobby lobby = Lobby.getInstance();
    private static final ValidationJsonData instance = new ValidationJsonData();

    private ValidationJsonData() {
    }

    public static ValidationJsonData getInstance() {
        return instance;
    }

    public boolean isValidSitOnTheTableEvent(Gambler gambler, int numberSeat, long tableId) {
        String nameRoom = String.format(Attribute.TABLE_WITH_HYPHEN, tableId);
        return gambler != null && numberSeat > -1 && tableId > -1 && lobby.containRoom(nameRoom)
                && lobby.findRoom(nameRoom).getTable().getSeatsCount() > numberSeat
                && lobby.findRoom(nameRoom).getTable().getSeats()[numberSeat] == null
                && gambler.getSittingOnTable() < 0 && nameRoom.equalsIgnoreCase(gambler.getTitleRoom());
    }
}