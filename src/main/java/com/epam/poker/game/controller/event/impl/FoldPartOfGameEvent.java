package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.game.entity.Table;
import com.epam.poker.game.logic.EventHandlerService;
import com.epam.poker.game.logic.PokerGameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.epam.poker.util.constant.Attribute.*;

public class FoldPartOfGameEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Lobby lobby = Lobby.getInstance();
    private static final FoldPartOfGameEvent instance = new FoldPartOfGameEvent();
    private static EventHandlerService eventHandlerService = EventHandlerService.getInstance();
    private static final List<String> PARTS_OF_GAMES = List.of(PRE_FLOP_PART_OF_GAME,
            FLOP_PART_OF_GAME, TURN_PART_OF_GAME, RIVER_PART_OF_GAME);

    private FoldPartOfGameEvent() {
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonLine);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        ObjectNode response = mapper.createObjectNode();
        response.putPOJO(EVENT, json.get(EVENT));
        ObjectNode objectNode = mapper.createObjectNode();
        if (gambler.getSittingOnTable() > -1
                && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
            Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
            int activeSeat = table.getActiveSeat();
            if (table.getSeats()[activeSeat] == gambler
                    && PARTS_OF_GAMES.contains(table.getPhaseGame())) {
                objectNode.put(SUCCESS, true);
                sendEvent(gambler, response, objectNode);
                eventHandlerService.gamblerFolded(table, gambler);
            }
        }
    }

    private void sendEvent(Gambler gambler, ObjectNode response, ObjectNode objectNode) {
        response.putPOJO(DATA, objectNode);
        try {
            gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
        } catch (IOException e) {
            LOGGER.error("Send JSON: " + e);
        }
    }

    public static FoldPartOfGameEvent getInstance() {
        return instance;
    }
}
