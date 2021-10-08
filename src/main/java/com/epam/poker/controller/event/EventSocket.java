package com.epam.poker.controller.event;

import com.epam.poker.model.game.Gambler;

public interface EventSocket {
    void execute(String jsonLine, Gambler gambler);

    static EventSocket of(String name) {
        return EventManagerGame.of(name);
    }
}
