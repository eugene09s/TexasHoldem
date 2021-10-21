package com.epam.poker.controller.event.impl;

import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.game.EventHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.epam.poker.util.constant.Attribute.*;

public class RaiseEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Lobby lobby = Lobby.getInstance();
    private static final EventHandlerService eventHandlerService = EventHandlerService.getInstance();
    private static final List<String> PARTS_OF_GAMES = List.of(PRE_FLOP_PART_OF_GAME,
            FLOP_PART_OF_GAME, TURN_PART_OF_GAME, RIVER_PART_OF_GAME);
    private static final RaiseEvent instance = new RaiseEvent();

    private RaiseEvent() {
    }

    @Override
    public void execute(String jsonRequest, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonRequest);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        BigDecimal amountRaise = BigDecimal.ZERO;
        try {
            String lineAmount = json.get(DATA).asText();
            amountRaise = new BigDecimal(lineAmount);
        } catch (NullPointerException e) {
            LOGGER.warn("Bet in the JSON not found. Error: " + e);
        }
        if (amountRaise.compareTo(BigDecimal.ZERO) > 0) {
            ObjectNode response = mapper.createObjectNode();
            response.putPOJO(EVENT, json.get(EVENT));
            ObjectNode objectNode = mapper.createObjectNode();
            if (gambler.getSittingOnTable() > -1
                    && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
                Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
                int activeSeat = table.getActiveSeat();
                if (table.getSeats()[activeSeat] == gambler
                        && PARTS_OF_GAMES.contains(table.getPhaseGame())
                        && table.getBiggestBet().compareTo(BigDecimal.ZERO) > 0) {//fixme perhapse should add check AllIn other gamblers
                    amountRaise = amountRaise.subtract(gambler.getBet());
                    if (amountRaise.compareTo(gambler.getMoneyInPlay()) <= 0) {
                        objectNode.put(SUCCESS, true);
                        sendEvent(gambler, response, objectNode);
                        try {
                            eventHandlerService.gamblerRaise(table, gambler, amountRaise);
                        } catch (ServiceException e) {
                            LOGGER.error("Service game error: " + e);
                        }
                    }
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

    public static RaiseEvent getInstance() {
        return instance;
    }
}
