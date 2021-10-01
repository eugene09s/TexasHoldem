package com.epam.poker.game.entity;

import com.epam.poker.model.entity.Entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Room implements Entity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private Map<String, Gambler> gamblers = Collections.synchronizedMap(new HashMap<>());
    private String titleRoom;
    private Table table;

    public Room(String titleRoom, Table table) {
        this.titleRoom = titleRoom;
        this.table = table;
    }

    public boolean addGambler(String username, Gambler gambler) {
        if (this.gamblers.put(username, gambler) != null) {
            gambler.setTitleRoom(this.titleRoom);
            return true;
        }
        return false;
    }

    public boolean deleteGambler(Gambler gambler) {
        if (this.gamblers.remove(gambler.getName(), gambler)) {
            gambler.setTitleRoom(null);
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
                sessionGambler.getBasicRemote().sendObject(jsonLine);
            } catch (IOException | EncodeException e) {
                LOGGER.error("Send event gambler: " + e);
            }
        }
        return isSuccess;
    }

    public void sendEventAllGamblers(String jsonLine) {
        if (jsonLine != null) {
            gamblers.forEach((k, v) -> {
                try {
                    v.getSession().getBasicRemote().sendObject(jsonLine);
                } catch (IOException | EncodeException e) {
                    LOGGER.error("Send json all users: " + e);
                }
            });
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (gamblers != null ? !gamblers.equals(room.gamblers) : room.gamblers != null) return false;
        if (titleRoom != null ? !titleRoom.equals(room.titleRoom) : room.titleRoom != null) return false;
        return table != null ? table.equals(room.table) : room.table == null;
    }

    @Override
    public int hashCode() {
        int result = gamblers != null ? gamblers.hashCode() : 0;
        result = 31 * result + (titleRoom != null ? titleRoom.hashCode() : 0);
        result = 31 * result + (table != null ? table.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Room.class.getSimpleName() + "[", "]")
                .add("gamblers=" + gamblers)
                .add("titleRoom='" + titleRoom + "'")
                .add("table=" + table)
                .toString();
    }
}
