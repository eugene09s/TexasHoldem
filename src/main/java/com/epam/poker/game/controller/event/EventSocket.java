package com.epam.poker.game.controller.event;

import com.epam.poker.game.entity.Gambler;

public interface EventSocket {
    void execute(String jsonLine, Gambler gambler);

    static EventSocket of(String name) {
        return EventManagerGame.of(name);
    }
}
