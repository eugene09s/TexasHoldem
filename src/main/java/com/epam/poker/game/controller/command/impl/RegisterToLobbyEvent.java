package com.epam.poker.game.controller.command.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.game.controller.command.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.service.user.UserService;
import com.epam.poker.model.service.user.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.websocket.EncodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

public class RegisterToLobbyEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final RegisterToLobbyEvent instance = new RegisterToLobbyEvent();
    private static Lobby lobby = Lobby.getInstance();
    private static UserService userService = UserServiceImpl.getInstance();

    private RegisterToLobbyEvent() {
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        ObjectNode response = mapper.createObjectNode();
        User user = null;
        try {
            user = userService.findUserByLogin(gambler.getName());
        } catch (ServiceException | DaoException e) {
            LOGGER.error("Select user from database: " + e);
        }
        BigDecimal balanceGambler;
        if (user != null) {
            balanceGambler = user.getBalance();
            response.put("success", true);
            response.put("username", gambler.getName());
            response.put("totalChips", balanceGambler);
        } else {
            response.put("success", false);
            response.put("message", "Gambler with name " + gambler.getName() + " was not found");
        }
        try {
            gambler.getSession().getBasicRemote().sendObject(response);
        } catch (IOException | EncodeException e) {
            LOGGER.error("Send JSON: " + e);
        }
    }

    public static RegisterToLobbyEvent getInstance() {
        return instance;
    }
}
