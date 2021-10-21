package com.epam.poker.controller.event;

import com.epam.poker.model.game.Gambler;

/**
 * The interface processing events socket.
 */
public interface EventSocket {
    /**
     * Execute event by request.
     *
     * @param jsonRequest the json request contains event name and data
     * @param gambler     the gambler which send that event
     */
    void execute(String jsonRequest, Gambler gambler);

    /**
     * Find handler by even name.
     *
     * @param name the name of event
     * @return the command that handles this event
     */
    static EventSocket of(String name) {
        return EventManagerGame.of(name);
    }
}
