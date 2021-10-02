package com.epam.poker.game.controller.event.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.game.ValidationJsonData;
import com.epam.poker.game.controller.event.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.game.entity.Table;
import com.epam.poker.model.entity.User;
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

public class SitOnTheTableEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SitOnTheTableEvent instance = new SitOnTheTableEvent();
    private static Lobby lobby = Lobby.getInstance();
    private static ValidationJsonData validationJsonData = ValidationJsonData.getInstance();
    private static UserService userService = UserServiceImpl.getInstance();
    private static final String MESSAGE_ERROR_CHIPS = "Your chips is invalid!";
    private static final String MESSAGE_ERROR_COMPARE_CHIPS_MAX_MIN_TABLE =
            "The amount of chips should be between the maximum and the minimum amount of allowed buy in";

    private SitOnTheTableEvent() {
    }

    public static SitOnTheTableEvent getInstance() {
        return instance;
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonLine);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        User user = null;
        try {
            user = userService.findUserByLogin(gambler.getName());
        } catch (ServiceException | DaoException e) {
            LOGGER.error("Select user from database: " + e);
        }
        JsonNode data = null;
        ObjectNode objectNode = mapper.createObjectNode();
        try {
            BigDecimal balanceGambler = user.getBalance();
            gambler.setBalance(balanceGambler);
            data = json.get(Attribute.DATA);
            int numberSeat = data.get(Attribute.SEAT).asInt();
            long tableId = data.get(Attribute.TABLE_ID).asLong();
            BigDecimal chips = new BigDecimal(String.valueOf(data.get(Attribute.CHIPS)));
            if (validationJsonData.isValidSitOnTheTableEvent(gambler, numberSeat, tableId)) {
                if (chips.compareTo(balanceGambler) >= 0) {
                    objectNode.put(Attribute.SUCCESS, false);
                    objectNode.put(Attribute.ERROR, MESSAGE_ERROR_CHIPS);
                } else if (!isValidMinMaxBetOnTable(tableId, chips)) {
                    objectNode.put(Attribute.SUCCESS, false);
                    objectNode.put(Attribute.ERROR, MESSAGE_ERROR_COMPARE_CHIPS_MAX_MIN_TABLE);
                } else {
                    objectNode.put(Attribute.SUCCESS, true);
                    lobby.findTableByNameRoom(String.format(Attribute.TABLE_WITH_HYPHEN, tableId))
                            .addGamblerOnTable(gambler, numberSeat);
                }
            } else {
                objectNode.put(Attribute.SUCCESS, false);
            }
            ObjectNode response = mapper.createObjectNode();
            response.putPOJO(Attribute.EVENT, json.get(Attribute.EVENT));
            response.putPOJO(Attribute.DATA, objectNode);
            try {
                gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException | NumberFormatException e) {
            LOGGER.error("JSON line don't exist data. " + e);
        }
    }

    private boolean isValidMinMaxBetOnTable(long tableId, BigDecimal chips)  {
        Table table = lobby.findTableByNameRoom(String.format(Attribute.TABLE_WITH_HYPHEN, tableId));
        if (table != null && chips.compareTo(table.getMaxBuyIn()) <= 0
            && chips.compareTo(table.getMinBuyIn()) >= 0) {
            return true;
        }
        return false;
    }
}
