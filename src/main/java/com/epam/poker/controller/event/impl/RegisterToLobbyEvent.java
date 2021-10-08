package com.epam.poker.controller.event.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.database.User;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

public class RegisterToLobbyEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Gson gson = new Gson();
    private static final Lobby lobby = Lobby.getInstance();
    private static UserService userService = UserServiceImpl.getInstance();
    private static final RegisterToLobbyEvent instance = new RegisterToLobbyEvent();

    private RegisterToLobbyEvent() {
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
        BigDecimal balanceGambler;
        ObjectNode objectNodes = mapper.createObjectNode();
        if (user != null) {
            balanceGambler = user.getBalance();
            gambler.setBalance(balanceGambler);
            objectNodes.put(Attribute.SUCCESS, true);
            objectNodes.put(Attribute.USERNAME, gambler.getName());
            objectNodes.put(Attribute.TOTAL_CHIPS, balanceGambler);
            objectNodes.put(Attribute.IMG, gambler.getImg());
        } else {
            objectNodes.put(Attribute.SUCCESS, false);
            objectNodes.put(Attribute.MESSAGE, "Gambler with name " + gambler.getName() + " was not found");
        }
        ObjectNode response  = mapper.createObjectNode();
        response.putPOJO(Attribute.EVENT, json.get(Attribute.EVENT));
        response.putPOJO(Attribute.DATA, objectNodes);
        try {
            gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
        } catch (IOException e) {
            LOGGER.error("Send JSON: " + e);
        }
    }

    public static RegisterToLobbyEvent getInstance() {
        return instance;
    }
}
