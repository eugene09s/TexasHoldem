package com.epam.poker.model.entity.game;

import com.epam.poker.model.entity.database.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Table implements Entity {
    private int id;
    private String name;
    private int seatsCount;
    private BigDecimal bigBlind;
    private BigDecimal smallBlind;
    private boolean privateTable;
    private int gamblersSittingInCount;
    private int gamblersInHandCount;
    private int lastGamblerToAct;
    private boolean gameIsOn;
    private boolean headsUp;
    private Gambler[] seats;
    private Deck deck;
    private BigDecimal minBuyIn;
    private BigDecimal maxBuyIn;
    private List<Pot> pots;
    private BigDecimal biggestBet;
    private Integer dealerSeat;
    private Integer activeSeat;
    private String phaseGame;
    private String[] board;
    private Log log;

    public Table(int id, String name, int seatsCount, BigDecimal bigBlind,
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
        this.seats = new Gambler[seatsCount];
        this.deck = new Deck();
        this.board = new String[]{"", "", "", "", ""};
        this.pots = new ArrayList<>(1);
        this.pots.add(new Pot());
        this.log = new Log();
    }

    public void deleteGamblerOfSeatByEntity(Gambler gambler) {
        for (int i = 0; i < this.seats.length; ++i) {
            if (gambler != null && this.seats[i] == gambler) {
                this.seats[i] = null;
            }
        }
    }

    public void deleteGamblerOfSeatByIndex(int numberOfSeats) {
        this.seats[numberOfSeats] = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
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

    public int getGamblersSittingInCount() {
        return gamblersSittingInCount;
    }

    public void setGamblersSittingInCount(int gamblersSittingInCount) {
        this.gamblersSittingInCount = gamblersSittingInCount;
    }

    public int getGamblersInHandCount() {
        return gamblersInHandCount;
    }

    public void setGamblersInHandCount(int gamblersInHandCount) {
        this.gamblersInHandCount = gamblersInHandCount;
    }

    public int getLastGamblerToAct() {
        return lastGamblerToAct;
    }

    public void setLastGamblerToAct(int lastGamblerToAct) {
        this.lastGamblerToAct = lastGamblerToAct;
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

    public Gambler[] getSeats() {
        return seats;
    }

    public void setSeats(Gambler[] seats) {
        this.seats = seats;
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

    public List<Pot> getPots() {
        return pots;
    }

    public boolean isEmptyPots() {
        return this.pots.get(0).getAmount().compareTo(BigDecimal.ZERO) == 0;
    }

    public Pot getPotByIndex(int index) {
        return this.pots.get(index);
    }

    public void setPots(List<Pot> pots) {
        this.pots = pots;
    }

    public BigDecimal getBiggestBet() {
        return biggestBet;
    }

    public void setBiggestBet(BigDecimal biggestBet) {
        this.biggestBet = biggestBet;
    }

    public Integer getDealerSeat() {
        return dealerSeat;
    }

    public void setDealerSeat(Integer dealerSeat) {
        this.dealerSeat = dealerSeat;
    }

    public Integer getActiveSeat() {
        return activeSeat;
    }

    public void setActiveSeat(Integer activeSeat) {
        this.activeSeat = activeSeat;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public BigDecimal getBigBlind() {
        return bigBlind;
    }

    public BigDecimal getSmallBlind() {
        return smallBlind;
    }
}