package com.epam.poker.controller.event.impl;

import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatSendMessageEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Lobby lobby = Lobby.getInstance();
    private static final ChatSendMessageEvent instance = new ChatSendMessageEvent();

    private ChatSendMessageEvent() {
    }

    public static ChatSendMessageEvent getInstance() {
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
        try {
            String message = json.get(Attribute.DATA).asText().trim();
            if (gambler.getTitleRoom() != null) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put(Attribute.SENDER, gambler.getName());
                objectNode.put(Attribute.IMG, gambler.getImg());
                objectNode.put(Attribute.MESSAGE, message);
                ObjectNode response = mapper.createObjectNode();
                response.put(Attribute.EVENT, Attribute.RECEIVE_MESSAGE_EVENT);
                response.putPOJO(Attribute.DATA, objectNode);
                lobby.findRoom(gambler.getTitleRoom()).broadcastEventExcept(String.valueOf(response), gambler);
            }
        } catch (NullPointerException e) {
            LOGGER.error("Parse data: " + e);
        }
    }
}
