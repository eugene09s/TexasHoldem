package com.epam.poker.util;

import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Pot;
import com.epam.poker.game.entity.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

import static com.epam.poker.util.constant.Attribute.*;

public class ParserDataToJson {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ParserDataToJson instance = new ParserDataToJson();

    private ParserDataToJson() {
    }

    public static ParserDataToJson getInstance() {
        return instance;
    }

    public String parseDataTableForRoom(Table table) {
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put(ID, table.getId());
        jsonNodes.put(NAME, table.getName());
        jsonNodes.put(SEATS_COUNT, table.getSeatsCount());
        jsonNodes.put(PLAYER_SEATED_COUNT, table.getPlayersSeatedCount());
        jsonNodes.put(BIG_BLIND, table.getBigBlind());
        jsonNodes.put(SMALL_BLIND, table.getSmallBlind());
        jsonNodes.put(MIN_BUY_IN, table.getMinBuyIn());
        jsonNodes.put(MAX_BUY_IN, table.getMinBuyIn());
        jsonNodes.putPOJO(POT, parsePot(table.getPot()));
        jsonNodes.put(BIGGEST_BET, table.getBiggestBet());
        jsonNodes.put(DEALER_SEAT, table.getDealerSeat());
        jsonNodes.put(ACTIVE_SEAT, table.getActiveSeat());
        jsonNodes.putPOJO(SEATS, parseArrayGambler(table.getSeats()));
        jsonNodes.put(PHASE, table.getPhaseGame());
        jsonNodes.putPOJO(BOARD, parseArray(table.getBoard()));
        ObjectNode nodeLog = mapper.createObjectNode();
        nodeLog.put(MESSAGE, table.getLog().getMessage());
        nodeLog.put(ACTION, table.getLog().getAction());
        nodeLog.put(SEAT, table.getLog().getSeat());
        nodeLog.put(NOTIFICATION, table.getLog().getNotification());
        jsonNodes.putPOJO(LOG, nodeLog);
        ObjectNode jsonNodeTable = mapper.createObjectNode();
        jsonNodeTable.putPOJO(TABLE, jsonNodes);
        return String.valueOf(jsonNodeTable);
    }

    private String parserGambler(Gambler gambler) {
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put(NAME, gambler.getName());
        jsonNodes.put(CHIPS_IN_PLAY, gambler.getMoneyInPlay());
        jsonNodes.put(SITTING_IN, gambler.isSittingIn());
        jsonNodes.put(IN_HAND, gambler.isInHand());
        jsonNodes.put(HAS_CARDS, gambler.isHasCards());
        jsonNodes.putPOJO(CARDS, parseArray(gambler.getPublicCards()));
        jsonNodes.put(BET, gambler.getBet());
        return String.valueOf(jsonNodes);
    }

    private ArrayNode parseArrayGambler(List<Gambler> gamblers) {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Gambler gambler : gamblers) {
            arrayNode.add(parserGambler(gambler));
        }
        return arrayNode;
    }

    public String parseDataTableForLobby(Table table) {
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put(ID, table.getId());
        jsonNodes.put(NAME, table.getName());
        jsonNodes.put(SEATS_COUNT, table.getSeatsCount());
        jsonNodes.put(PLAYER_SEATED_COUNT, table.getPlayersSeatedCount());
        jsonNodes.put(BIG_BLIND, table.getBigBlind());
        jsonNodes.put(SMALL_BLIND, table.getSmallBlind());
        return String.valueOf(jsonNodes);
    }

    private ArrayNode parseArray(String[] lines) {
        ArrayNode arrayNode = mapper.createArrayNode();
        if (lines != null) {
            for (String line : lines) {
                arrayNode.add(line);
            }
        }
        return arrayNode;
    }

    private ArrayNode parsePot(Pot pot) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put(AMOUNT, pot.getAmount());
        ArrayNode arrayNodeContributors = mapper.createArrayNode();
        List<Integer> contributors = pot.getContributors();
        for (int value : contributors) {
            arrayNodeContributors.add(value);
        }
        objectNode.putPOJO(CONTRIBUTORS, arrayNodeContributors);
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(objectNode);
        return arrayNode;
    }
}
