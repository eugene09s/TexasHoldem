package com.epam.poker.controller.event.util;

import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.util.constant.Attribute;

public class ValidationSitOnTableEventHelper {
    private static final Lobby lobby = Lobby.getInstance();
    private static final ValidationSitOnTableEventHelper instance = new ValidationSitOnTableEventHelper();

    private ValidationSitOnTableEventHelper() {
    }

    public static ValidationSitOnTableEventHelper getInstance() {
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
