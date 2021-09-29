package com.epam.poker.game.controller.command.impl;

import com.epam.poker.game.controller.command.EventSocket;
import com.epam.poker.game.entity.Gambler;

public class LeaveFromRoomEvent implements EventSocket {
    @Override
    public void execute(String jsonLine, Gambler gambler) {

    }
}
