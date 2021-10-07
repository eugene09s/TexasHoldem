package com.epam.poker.model.service.game;

import com.epam.poker.model.entity.game.Gambler;
import com.epam.poker.model.entity.game.Lobby;
import com.epam.poker.model.entity.game.Log;
import com.epam.poker.model.entity.game.Table;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class NotifierTableDataService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EVENT_NAME_TABLE_DATA = "table-data";
    private static final String EVENT_NAME_GAME_STOP = "gameStopped";
    private static final Lobby lobby = Lobby.getInstance();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ParserDataToJsonService parserDataToJson = ParserDataToJsonService.getInstance();
    private static final NotifierTableDataService instance = new NotifierTableDataService();

    private NotifierTableDataService() {
    }

    public static NotifierTableDataService getInstance() {
        return instance;
    }

    public void notifyALLGamblersOfRoom(Table table) {
        long tableId = table.getId();
        String nameRoom = String.format(Attribute.TABLE_WITH_HYPHEN, tableId);
        ObjectNode response = mapper.createObjectNode();
        response.put(Attribute.EVENT, EVENT_NAME_TABLE_DATA);
        response.putPOJO(Attribute.DATA, parserDataToJson.parseDataTableForRoom(table));
        lobby.findRoom(nameRoom).broadcastEvent(String.valueOf(response));
        table.setLog(new Log());
    }

    public void notifyALLGamblersOfRoomGameStop(Table table) {
        long tableId = table.getId();
        String nameRoom = String.format(Attribute.TABLE_WITH_HYPHEN, tableId);
        ObjectNode response = mapper.createObjectNode();
        response.put(Attribute.EVENT, EVENT_NAME_GAME_STOP);
        response.putPOJO(Attribute.DATA, parserDataToJson.parseDataTableForRoom(table));
        lobby.findRoom(nameRoom).broadcastEvent(String.valueOf(response));
        table.setLog(new Log());
    }

    public void notifyGambler(String eventName, Gambler gambler, ObjectNode data) {
        ObjectNode response = mapper.createObjectNode();
        response.put(Attribute.EVENT, eventName);
        response.putPOJO(Attribute.DATA, data);
        try {
            gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
        } catch (IOException e) {
            LOGGER.error("Send json to gambler-" + gambler.getName() + ". Error: " + e);
        }
    }
}
