package com.epam.poker.game.controller.event.impl;

import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.game.entity.Table;
import com.epam.poker.game.logic.PokerGameService;
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

public class PostBlindPartOfGameEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Lobby lobby = Lobby.getInstance();
    private static final PostBlindPartOfGameEvent instance = new PostBlindPartOfGameEvent();
    private static PokerGameService pokerGameService = PokerGameService.getInstacne();
    private static final List<String> PARTS_OF_GAMES = List.of(PRE_FLOP_PART_OF_GAME,
            FLOP_PART_OF_GAME, TURN_PART_OF_GAME, RIVER_PART_OF_GAME);
    private PostBlindPartOfGameEvent() {
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonLine);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        boolean postedBlind = json.get(DATA).asBoolean();
        ObjectNode response = mapper.createObjectNode();
        response.putPOJO(EVENT, json.get(EVENT));
        ObjectNode objectNode = mapper.createObjectNode();
        if (gambler.getSittingOnTable() > -1
                && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
            Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
            int activeSeat = table.getActiveSeat();
            if (table.getSeats()[activeSeat] == gambler
                    && (table.getPhaseGame().equals(SMALL_BLIND)
                    || table.getPhaseGame().equals(BIG_BLIND))) {
                if (postedBlind) {
                    objectNode.put(SUCCESS, true);
                    sendEvent(gambler, response, objectNode);
                    if (table.getPhaseGame().equals(SMALL_BLIND)) {
                        pokerGameService.gamblerPostedSmallBlind(table, gambler);
                    } else {
                        pokerGameService.gamblerPostedBigBlind(table, gambler);
                    }
                } else {
                    pokerGameService.gamblerSatOut(table, gambler, false);
                    objectNode.put(SUCCESS, true);
                    sendEvent(gambler, response, objectNode);
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

    public static PostBlindPartOfGameEvent getInstance() {
        return instance;
    }
}
