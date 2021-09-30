package com.epam.poker.game.entity;

import java.math.BigDecimal;
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
        Room room = new Room("table-0",
                new Table(0, "10-handed Table", 10, new BigDecimal(2), new BigDecimal(1),
                        new BigDecimal(200), new BigDecimal(40), false));
        rooms.put("table-0", room);
        Room room1 = new Room("table-1",
                new Table(1, "6-handed Table", 6, new BigDecimal(4), new BigDecimal(2),
                        new BigDecimal(400), new BigDecimal(80), false));
        rooms.put("table-1", room1);
//        Room room2 = new Room("table-2",
//                new Table(2, "2-handed Table", 2, new BigDecimal(8), new BigDecimal(4),
//                        new BigDecimal(800), new BigDecimal(160), false));
//        rooms.put("table-2", room);
//        Room room3 = new Room("table-3",
//                new Table(3, "6-handed Table Private Table", 6, new BigDecimal(20), new BigDecimal(10),
//                        new BigDecimal(2000), new BigDecimal(400), true));
//        rooms.put("table-3", room);
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

    public List<Table> findAllTables() {
        List<Table> tables = new ArrayList<>();
        rooms.forEach((k, v) -> {
            tables.add(v.getTable());
        });
        return tables;
    }
}
