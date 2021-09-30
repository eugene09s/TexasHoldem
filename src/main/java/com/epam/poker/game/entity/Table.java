package com.epam.poker.game.entity;

import com.epam.poker.model.entity.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Table implements Entity {
    private long id;
    private String name;
    private int seatsCount;
    private int playersSeatedCount;
    private BigDecimal bigBlind;
    private BigDecimal smallBlind;
    private boolean privateTable;
    private int playersSittingInCount;
    private int playersInHandCount;
    private String lastPlayerToAct;
    private boolean gameIsOn;
    private boolean headsUp;
    private List<Gambler> seats;
    private Deck deck;
//    private eventEmitter;
    private BigDecimal minBuyIn;
    private BigDecimal maxBuyIn;
    private Pot pot;
    private BigDecimal biggestBet;
    private int dealerSeat;
    private int activeSeat;
    private String[] seatsPlace;
    private String phaseGame;
    private String[] board;
    private Log log;

    public Table(long id, String name, int seatsCount, BigDecimal bigBlind,
                 BigDecimal smallBlind, BigDecimal maxBuyIn, BigDecimal minBuyIn, boolean privateTable) {
        this.id = id;
        this.name = name;
        this.seatsCount = seatsCount;
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;
        this.maxBuyIn = maxBuyIn;
        this.minBuyIn = minBuyIn;
        this.biggestBet = new BigDecimal(0);
        this.privateTable = privateTable;
        this.seats = new ArrayList<>(seatsCount);
        this.deck = new Deck();
        this.board = new String[]{"", "", "", "", ""};
        this.pot = new Pot();
        this.log = new Log();
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public void setPlayersSeatedCount(int playersSeatedCount) {
        this.playersSeatedCount = playersSeatedCount;
    }

    public void setBigBlind(BigDecimal bigBlind) {
        this.bigBlind = bigBlind;
    }

    public void setSmallBlind(BigDecimal smallBlind) {
        this.smallBlind = smallBlind;
    }

    public boolean isPrivateTable() {
        return privateTable;
    }

    public void setPrivateTable(boolean privateTable) {
        this.privateTable = privateTable;
    }

    public int getPlayersSittingInCount() {
        return playersSittingInCount;
    }

    public void setPlayersSittingInCount(int playersSittingInCount) {
        this.playersSittingInCount = playersSittingInCount;
    }

    public int getPlayersInHandCount() {
        return playersInHandCount;
    }

    public void setPlayersInHandCount(int playersInHandCount) {
        this.playersInHandCount = playersInHandCount;
    }

    public String getLastPlayerToAct() {
        return lastPlayerToAct;
    }

    public void setLastPlayerToAct(String lastPlayerToAct) {
        this.lastPlayerToAct = lastPlayerToAct;
    }

    public boolean isGameIsOn() {
        return gameIsOn;
    }

    public void setGameIsOn(boolean gameIsOn) {
        this.gameIsOn = gameIsOn;
    }

    public boolean isHeadsUp() {
        return headsUp;
    }

    public void setHeadsUp(boolean headsUp) {
        this.headsUp = headsUp;
    }

    public List<Gambler> getSeats() {
        return seats;
    }

    public void setSeats(List<Gambler> seats) {
        this.seats = seats;
    }

    public void addGamblerToSeats(Gambler gambler) {
        this.seats.add(gambler);
    }

    public void deleteGamblerToSeats(Gambler gambler) {
        this.seats.remove(gambler);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public BigDecimal getMinBuyIn() {
        return minBuyIn;
    }

    public void setMinBuyIn(BigDecimal minBuyIn) {
        this.minBuyIn = minBuyIn;
    }

    public BigDecimal getMaxBuyIn() {
        return maxBuyIn;
    }

    public void setMaxBuyIn(BigDecimal maxBuyIn) {
        this.maxBuyIn = maxBuyIn;
    }

    public Pot getPot() {
        return pot;
    }

    public void setPot(Pot pot) {
        this.pot = pot;
    }

    public BigDecimal getBiggestBet() {
        return biggestBet;
    }

    public void setBiggestBet(BigDecimal biggestBet) {
        this.biggestBet = biggestBet;
    }

    public int getDealerSeat() {
        return dealerSeat;
    }

    public void setDealerSeat(int dealerSeat) {
        this.dealerSeat = dealerSeat;
    }

    public int getActiveSeat() {
        return activeSeat;
    }

    public void setActiveSeat(int activeSeat) {
        this.activeSeat = activeSeat;
    }

    public String[] getSeatsPlace() {
        return seatsPlace;
    }

    public void setSeatsPlace(String[] seatsPlace) {
        this.seatsPlace = seatsPlace;
    }

    public String getPhaseGame() {
        return phaseGame;
    }

    public void setPhaseGame(String phaseGame) {
        this.phaseGame = phaseGame;
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public int getPlayersSeatedCount() {
        return playersSeatedCount;
    }

    public BigDecimal getBigBlind() {
        return bigBlind;
    }

    public BigDecimal getSmallBlind() {
        return smallBlind;
    }
}
