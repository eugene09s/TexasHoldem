package com.epam.poker.model.game;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.Entity;
import com.epam.poker.service.game.EventHandlerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Room implements Entity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static EventHandlerService eventHandlerService = EventHandlerService.getInstance();
    private StatisticResultGame statisticResultGame = new StatisticResultGame();
    private Map<String, Gambler> gamblers = Collections.synchronizedMap(new HashMap<>());
    private String titleRoom;
    private Table table;

    public Room(String titleRoom, Table table) {
        this.titleRoom = titleRoom;
        this.table = table;
    }

    public boolean addGambler(String username, Gambler gambler) {
        if (this.gamblers.put(username, gambler) == null) {
            gambler.setTitleRoom(this.titleRoom);
            return true;
        }
        return false;
    }

    public boolean deleteGambler(Gambler gambler) {
        if (this.gamblers.remove(gambler.getName()) != null) {
            gambler.setTitleRoom(null);
            if (gambler.getSittingOnTable() > -1
                    && this.table.getSeats()[gambler.getNumberSeatOnTable()].equals(gambler)) {
                try {
                    eventHandlerService.gamblerLeft(this.table, gambler);
                } catch (ServiceException e) {
                    LOGGER.error("Service game error: " + e);
                }
            }
            return true;
        }
        return false;
    }

    public boolean sendEventGambler(String jsonLine, String nameGambler) {
        boolean isSuccess = false;
        Gambler gambler = gamblers.get(nameGambler);
        if (gambler != null && jsonLine != null) {
            Session sessionGambler = gambler.getSession();
            try {
                sessionGambler.getBasicRemote().sendText(jsonLine);
            } catch (IOException e) {
                LOGGER.error("Send event gambler: " + e);
            }
        }
        return isSuccess;
    }

    public void broadcastEvent(String jsonLine) {
        if (jsonLine != null) {
            gamblers.forEach((k, v) -> {
                try {
                    v.getSession().getBasicRemote().sendText(jsonLine);
                } catch (IOException e) {
                    LOGGER.error("Send json all users: " + e);
                }
            });
        }
    }

    public void broadcastEventExcept(String jsonLine, Gambler gambler) {
        String gamblerName = gambler.getName();
        if (jsonLine != null) {
            gamblers.forEach((k, v) -> {
                if (k != gamblerName) {
                    try {
                        v.getSession().getBasicRemote().sendText(jsonLine);
                    } catch (IOException e) {
                        LOGGER.error("Send json all users: " + e);
                    }
                }
            });
        }
    }

    public StatisticResultGame getStatisticResultGame() {
        return statisticResultGame;
    }

    public Room setStatisticResultGame(StatisticResultGame statisticResultGame) {
        this.statisticResultGame = statisticResultGame;
        return this;
    }

    public static EventHandlerService getEventHandlerService() {
        return eventHandlerService;
    }

    public static void setEventHandlerService(EventHandlerService eventHandlerService) {
        Room.eventHandlerService = eventHandlerService;
    }

    public Map<String, Gambler> getGamblers() {
        return gamblers;
    }

    public Room setGamblers(Map<String, Gambler> gamblers) {
        this.gamblers = gamblers;
        return this;
    }

    public String getTitleRoom() {
        return titleRoom;
    }

    public Room setTitleRoom(String titleRoom) {
        this.titleRoom = titleRoom;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public Room setTable(Table table) {
        this.table = table;
        return this;
    }
}
