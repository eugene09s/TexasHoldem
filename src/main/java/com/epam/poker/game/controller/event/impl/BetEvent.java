package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.game.entity.Table;
import com.epam.poker.game.logic.EventHandlerService;
import com.epam.poker.game.logic.PokerGameService;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.epam.poker.util.constant.Attribute.*;
import static com.epam.poker.util.constant.Attribute.RIVER_PART_OF_GAME;

public class BetEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Lobby lobby = Lobby.getInstance();
    private static final BetEvent instance = new BetEvent();
    private static EventHandlerService eventHandlerService = EventHandlerService.getInstance();
    private static final List<String> PARTS_OF_GAMES = List.of(PRE_FLOP_PART_OF_GAME,
            FLOP_PART_OF_GAME, TURN_PART_OF_GAME, RIVER_PART_OF_GAME);

    private BetEvent() {
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonLine);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        BigDecimal bet = BigDecimal.ZERO;
        try {
            String  lineBet = json.get(DATA).asText();
            bet = new BigDecimal(lineBet);
        } catch (NullPointerException e) {
            LOGGER.warn("Bet in the JSON not found. Error: " + e);
        }
        if (bet.compareTo(BigDecimal.ZERO) > 0) {
            ObjectNode response = mapper.createObjectNode();
            response.putPOJO(EVENT, json.get(EVENT));
            ObjectNode objectNode = mapper.createObjectNode();
            if (gambler.getSittingOnTable() > -1
                    && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
                Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
                int activeSeat = table.getActiveSeat();
                if (table.getSeats()[activeSeat] == gambler
                        && PARTS_OF_GAMES.contains(table.getPhaseGame())
                        && bet.compareTo(gambler.getMoneyInPlay()) <= 0) {
                    objectNode.put(SUCCESS, true);
                    sendEvent(gambler, response, objectNode);
                    eventHandlerService.gamblerBetted(table, gambler, bet);
                }
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

    public static BetEvent getInstance() {
        return instance;
    }
}
