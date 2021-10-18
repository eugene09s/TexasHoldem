package com.epam.poker.model.game;

import com.epam.poker.model.Entity;
import com.epam.poker.util.constant.Attribute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Lobby implements Entity {
    private static final AtomicBoolean isLobbyCreated = new AtomicBoolean();
    private static final ReentrantLock instanceLocker = new ReentrantLock();
    private static final Map<String, Gambler> users = new ConcurrentHashMap<>();
    private static final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private static Lobby instance;

    static {
        String titleRoom = String.format(Attribute.TABLE_WITH_HYPHEN, 0);
        Room room = new Room(titleRoom,
                new Table(0, "10-handed Table", 10, new BigDecimal(2), new BigDecimal(1),
                        new BigDecimal(200), new BigDecimal(40), false));
        rooms.put(titleRoom, room);
        String titleRoom1 = String.format(Attribute.TABLE_WITH_HYPHEN, 1);
        Room room1 = new Room(titleRoom1,
                new Table(1, "6-handed Table", 6, new BigDecimal(4), new BigDecimal(2),
                        new BigDecimal(400), new BigDecimal(80), false));
        rooms.put(titleRoom1, room1);
        String titleRoom2 = String.format(Attribute.TABLE_WITH_HYPHEN, 2);
        Room room2 = new Room(titleRoom2,
                new Table(2, "2-handed Table", 2, new BigDecimal(8), new BigDecimal(4),
                        new BigDecimal(800), new BigDecimal(160), false));
        rooms.put(titleRoom2, room2);
        String titleRoom3 = String.format(Attribute.TABLE_WITH_HYPHEN, 3);
        Room room3 = new Room(titleRoom3,
                new Table(3, "6-handed Table", 6, new BigDecimal(20), new BigDecimal(10),
                        new BigDecimal(2000), new BigDecimal(400), false));
        rooms.put(titleRoom3, room3);
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

    public Gambler deleteGambler(String username) {
        return users.remove(username);
    }

    public Room findRoom(String titleRoom) {
        return rooms.get(titleRoom);
    }

    public Table findTableByNameRoom(String titleRoom) {
        return rooms.get(titleRoom).getTable();
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
            isSuccess = room.deleteGambler(gambler);
        }
        return isSuccess;
    }

    public boolean containRoom(String nameRoom) {
        return rooms.containsKey(nameRoom);
    }

    public List<Table> findAllTables() {
        List<Table> tables = new ArrayList<>();
        rooms.forEach((k, v) -> {
            tables.add(v.getTable());
        });
        return tables;
    }
}
