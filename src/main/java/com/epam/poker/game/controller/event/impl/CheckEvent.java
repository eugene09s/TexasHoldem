package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.ValidationJsonData;
import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.game.entity.Table;
import com.epam.poker.game.logic.PokerGameService;
import com.epam.poker.model.service.user.UserService;
import com.epam.poker.model.service.user.impl.UserServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Lobby lobby = Lobby.getInstance();
    private static final CheckEvent instance = new CheckEvent();
    private static PokerGameService pokerGameService = PokerGameService.getInstacne();
    private static final String PREFLOP = "preflop";
    private static final List<String> PARTS_OF_GAMES = List.of(PREFLOP, "flop", "turn", "river");

    private CheckEvent() {
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
        ObjectNode objectNode = mapper.createObjectNode();
        if (gambler.getSittingOnTable() > -1
                && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
            Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
            int activeSeat = table.getActiveSeat();
            if (table.getSeats()[activeSeat] == gambler
                    && table.getBiggestBet().equals(BigDecimal.ZERO)
                    || (table.getPhaseGame() == PREFLOP && table.getBiggestBet() == gambler.getBet())
                    && PARTS_OF_GAMES.contains(table.getPhaseGame())) {
                objectNode.put(Attribute.SUCCESS, true);
                pokerGameService.gamblerChecked(table, gambler);
            }
        }
        response.putPOJO(Attribute.EVENT, json.get(Attribute.EVENT));
        response.putPOJO(Attribute.DATA, objectNode);
        try {
            gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
        } catch (IOException e) {
            LOGGER.error("Send JSON: " + e);
        }
    }

    public static CheckEvent getInstance() {
        return instance;
    }
}
