package com.epam.poker.game.lobby;

import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Table;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Lobby {
    private static final AtomicBoolean isLobbyCreated = new AtomicBoolean();
    private static final ReentrantLock instanceLocker = new ReentrantLock();
    private static final Map<String, Gambler> users = Collections.synchronizedMap(new HashMap<>());
    private static final Map<String, Room> rooms = Collections.synchronizedMap(new HashMap<>());
    private static Lobby instance;

    static {
        Room room = new Room("table-0", new Table(8, 2, "Sample 2-handed Table", 0, 2, 4));
        rooms.put("table-0", room);
        Room room1 = new Room("table-1", new Table(4, 1, "Sample 6-handed Table", 0, 6, 2));
        rooms.put("table-1", room1);
        Room room2 = new Room("table-2", new Table(2, 0, "Sample 10-handed Table", 0, 10, 1));
        rooms.put("table-2", room2);
        Room room3 = new Room("table-3", new Table(2, 3, "Come dude", 0, 5, 1));
        rooms.put("table-3", room3);
    }

    private Lobby() {
    }

    public static Lobby getInstance() {
        if (!isLobbyCreated.get()) {
            instanceLocker.lock();
            try {
                if (!isLobbyCreated.get()) {
                    instance = new Lobby();
                }
            } finally {
                instanceLocker.unlock();
            }
        }
        return instance;
    }

    public void addRoom(String titleRoom, Room room) {
        rooms.put(titleRoom, room);
    }

    public void deleteRoom(String titleRoom) {
        rooms.remove(titleRoom);
    }

    public boolean addGambler(String username, Gambler gambler) {
        return users.put(username, gambler) != null;
    }

    public boolean deleteGambler(String username, Gambler gambler) {
        return users.remove(username, gambler);
    }

    public Room findRoom(String titleRoom) {
        return this.rooms.get(titleRoom);
    }

    public boolean addGamblerToRoom(String titleRoom, Gambler gambler) {
        boolean isSuccess = false;
        Room room = rooms.get(titleRoom);
        if (room != null) {
            isSuccess = room.addGambler(gambler.getName(), gambler);
        }
        return isSuccess;
    }

    public boolean deleteGamblerToRoom(String titleRoom, Gambler gambler) {
        boolean isSuccess = false;
        Room room = rooms.get(titleRoom);
        if (room != null) {
            isSuccess = room.deleteGambler(gambler.getName(), gambler);
        }
        return isSuccess;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public List<Table> findAllTables() {
        List<Table> tables = new ArrayList<>();
        rooms.forEach((k, v) -> {
            tables.add(v.getTable());
        });
        return tables;
    }
}
