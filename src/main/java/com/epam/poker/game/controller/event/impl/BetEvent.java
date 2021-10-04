package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;

public class BetEvent implements EventSocket {
    @Override
    public void execute(String jsonLine, Gambler gambler) {


    }
}
