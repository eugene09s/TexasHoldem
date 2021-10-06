package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;

public class RaisePartOfGameEvent implements EventSocket {
    private static final RaisePartOfGameEvent instance = new RaisePartOfGameEvent();

    private RaisePartOfGameEvent() {
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        //todo raise part of game
    }

    public static RaisePartOfGameEvent getInstance() {
        return instance;
    }
}
