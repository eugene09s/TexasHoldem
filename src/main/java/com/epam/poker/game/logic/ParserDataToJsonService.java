package com.epam.poker.game.logic;

import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Pot;
import com.epam.poker.game.entity.Table;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

import static com.epam.poker.util.constant.Attribute.*;

public class ParserDataToJsonService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ParserDataToJsonService instance = new ParserDataToJsonService();

    private ParserDataToJsonService() {
    }

    public static ParserDataToJsonService getInstance() {
        return instance;
    }

    public ObjectNode parseDataTableForRoom(Table table) {
        ObjectNode jsonNodes = mapper.createObjectNode()
                .put(ID, table.getId())
                .put(NAME, table.getName())
                .put(SEATS_COUNT, table.getSeatsCount())
                .put(PLAYER_SEATED_COUNT, table.getGamblersSeatedCount())
                .put(BIG_BLIND, table.getBigBlind())
                .put(SMALL_BLIND, table.getSmallBlind())
                .put(MIN_BUY_IN, table.getMinBuyIn())
                .put(MAX_BUY_IN, table.getMaxBuyIn());
        List<Pot> pots = table.getPots();
        for (Pot pot : pots) {
            jsonNodes.putPOJO(POT, parsePot(pot));
        }
        jsonNodes.put(BIGGEST_BET, table.getBiggestBet())
                .put(DEALER_SEAT, table.getDealerSeat())
                .put(ACTIVE_SEAT, table.getActiveSeat())
                .putPOJO(SEATS, parseArrayGambler(table.getSeats()))
                .put(PHASE, table.getPhaseGame())
                .putPOJO(BOARD, parseArray(table.getBoard()));
        ObjectNode nodeLog = mapper.createObjectNode();
        nodeLog.put(MESSAGE, table.getLog().getMessage())
                .put(ACTION, table.getLog().getAction())
                .put(SEAT, table.getLog().getSeat())
                .put(NOTIFICATION, table.getLog().getNotification());
        jsonNodes.putPOJO(LOG, nodeLog);
        return jsonNodes;
    }

    private ObjectNode parserGambler(Gambler gambler) {
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put(NAME, gambler.getName())
                .put(CHIPS_IN_PLAY, gambler.getMoneyInPlay())
                .put(SITTING_IN, gambler.isSittingIn())
                .put(IN_HAND, gambler.isInHand())
                .put(HAS_CARDS, gambler.isHasCards())
                .putPOJO(CARDS, parseArray(gambler.getPublicCards()))
                .put(BET, gambler.getBet());
        return jsonNodes;
    }

    private ArrayNode parseArrayGambler(Gambler[] gamblers) {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (Gambler gambler : gamblers) {
            if (gambler != null) {
                arrayNode.add(parserGambler(gambler));
            } else {
                arrayNode.add((JsonNode) null);
            }
        }
        return arrayNode;
    }

    public String parseDataTableForLobby(Table table) {
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put(ID, table.getId())
                .put(NAME, table.getName())
                .put(SEATS_COUNT, table.getSeatsCount())
                .put(PLAYER_SEATED_COUNT, table.getGamblersSeatedCount())
                .put(BIG_BLIND, table.getBigBlind())
                .put(SMALL_BLIND, table.getSmallBlind());
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
