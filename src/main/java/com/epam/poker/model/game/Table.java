package com.epam.poker.model.game;

import com.epam.poker.model.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (id != table.id) return false;
        if (seatsCount != table.seatsCount) return false;
        if (privateTable != table.privateTable) return false;
        if (gamblersSittingInCount != table.gamblersSittingInCount) return false;
        if (gamblersInHandCount != table.gamblersInHandCount) return false;
        if (lastGamblerToAct != table.lastGamblerToAct) return false;
        if (gameIsOn != table.gameIsOn) return false;
        if (headsUp != table.headsUp) return false;
        if (name != null ? !name.equals(table.name) : table.name != null) return false;
        if (bigBlind != null ? !bigBlind.equals(table.bigBlind) : table.bigBlind != null) return false;
        if (smallBlind != null ? !smallBlind.equals(table.smallBlind) : table.smallBlind != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(seats, table.seats)) return false;
        if (deck != null ? !deck.equals(table.deck) : table.deck != null) return false;
        if (minBuyIn != null ? !minBuyIn.equals(table.minBuyIn) : table.minBuyIn != null) return false;
        if (maxBuyIn != null ? !maxBuyIn.equals(table.maxBuyIn) : table.maxBuyIn != null) return false;
        if (pots != null ? !pots.equals(table.pots) : table.pots != null) return false;
        if (biggestBet != null ? !biggestBet.equals(table.biggestBet) : table.biggestBet != null) return false;
        if (dealerSeat != null ? !dealerSeat.equals(table.dealerSeat) : table.dealerSeat != null) return false;
        if (activeSeat != null ? !activeSeat.equals(table.activeSeat) : table.activeSeat != null) return false;
        if (phaseGame != null ? !phaseGame.equals(table.phaseGame) : table.phaseGame != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(board, table.board)) return false;
        return log != null ? log.equals(table.log) : table.log == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + seatsCount;
        result = 31 * result + (bigBlind != null ? bigBlind.hashCode() : 0);
        result = 31 * result + (smallBlind != null ? smallBlind.hashCode() : 0);
        result = 31 * result + (privateTable ? 1 : 0);
        result = 31 * result + gamblersSittingInCount;
        result = 31 * result + gamblersInHandCount;
        result = 31 * result + lastGamblerToAct;
        result = 31 * result + (gameIsOn ? 1 : 0);
        result = 31 * result + (headsUp ? 1 : 0);
        result = 31 * result + Arrays.hashCode(seats);
        result = 31 * result + (deck != null ? deck.hashCode() : 0);
        result = 31 * result + (minBuyIn != null ? minBuyIn.hashCode() : 0);
        result = 31 * result + (maxBuyIn != null ? maxBuyIn.hashCode() : 0);
        result = 31 * result + (pots != null ? pots.hashCode() : 0);
        result = 31 * result + (biggestBet != null ? biggestBet.hashCode() : 0);
        result = 31 * result + (dealerSeat != null ? dealerSeat.hashCode() : 0);
        result = 31 * result + (activeSeat != null ? activeSeat.hashCode() : 0);
        result = 31 * result + (phaseGame != null ? phaseGame.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(board);
        result = 31 * result + (log != null ? log.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", seatsCount=").append(seatsCount);
        sb.append(", bigBlind=").append(bigBlind);
        sb.append(", smallBlind=").append(smallBlind);
        sb.append(", privateTable=").append(privateTable);
        sb.append(", gamblersSittingInCount=").append(gamblersSittingInCount);
        sb.append(", gamblersInHandCount=").append(gamblersInHandCount);
        sb.append(", lastGamblerToAct=").append(lastGamblerToAct);
        sb.append(", gameIsOn=").append(gameIsOn);
        sb.append(", headsUp=").append(headsUp);
        sb.append(", seats=").append(Arrays.toString(seats));
        sb.append(", deck=").append(deck);
        sb.append(", minBuyIn=").append(minBuyIn);
        sb.append(", maxBuyIn=").append(maxBuyIn);
        sb.append(", pots=").append(pots);
        sb.append(", biggestBet=").append(biggestBet);
        sb.append(", dealerSeat=").append(dealerSeat);
        sb.append(", activeSeat=").append(activeSeat);
        sb.append(", phaseGame='").append(phaseGame).append('\'');
        sb.append(", board=").append(Arrays.toString(board));
        sb.append(", log=").append(log);
        sb.append('}');
        return sb.toString();
    }
}