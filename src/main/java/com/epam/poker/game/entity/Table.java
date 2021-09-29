package com.epam.poker.game.entity;

public class Table {
    private int bigBlind;
    private int id;
    private String name;
    private int playersSeatedCount;
    private int seatsCount;
    private int smallBlind;

    public Table(int bigBlind, int id,
                 String name, int playersSeatedCount,
                 int seatsCount, int smallBlind) {
        this.bigBlind = bigBlind;
        this.id = id;
        this.name = name;
        this.playersSeatedCount = playersSeatedCount;
        this.seatsCount = seatsCount;
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public Table setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
        return this;
    }

    public int getId() {
        return id;
    }

    public Table setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Table setName(String name) {
        this.name = name;
        return this;
    }

    public int getPlayersSeatedCount() {
        return playersSeatedCount;
    }

    public Table setPlayersSeatedCount(int playersSeatedCount) {
        this.playersSeatedCount = playersSeatedCount;
        return this;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public Table setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
        return this;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public Table setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table that = (Table) o;

        if (bigBlind != that.bigBlind) return false;
        if (id != that.id) return false;
        if (playersSeatedCount != that.playersSeatedCount) return false;
        if (seatsCount != that.seatsCount) return false;
        if (smallBlind != that.smallBlind) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = bigBlind;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + playersSeatedCount;
        result = 31 * result + seatsCount;
        result = 31 * result + smallBlind;
        return result;
    }

//    public static LobbyTableBuilder builder() {
//       return new LobbyTableBuilder();
//    }
//
//    public static class LobbyTableBuilder {
//       private LobbyTable lobbyTable;
//
//       public LobbyTableBuilder() {
//           lobbyTable = new LobbyTable();
//       }
//
//       public LobbyTable createLobbyTable
//    }
}
