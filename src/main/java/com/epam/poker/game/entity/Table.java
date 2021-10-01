package com.epam.poker.game.entity;

import com.epam.poker.model.entity.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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
        this.seatsPlace = new String[seatsCount];
        this.privateTable = privateTable;
        this.seats = new ArrayList<>(seatsCount);
        this.deck = new Deck();
        this.board = new String[]{"", "", "", "", ""};
        this.pot = new Pot();
        this.log = new Log();
        this.dealerSeat = -1;
        this.activeSeat = -1;
    }

    public void addGamblerOnTable(Gambler gambler, int numberSeat) {
        this.seats.add(numberSeat, gambler);
        gambler.setSittingOnTable(this.id);
        gambler.setNumberSeatOnTable(numberSeat);
//        gambler.setMoneyInPlay(gambler.getBalance());
        this.playersSeatedCount++;
        playerSatIn(gambler);
    }

    private void playerSatIn(Gambler gambler) {
        this.log.setMessage(gambler.getName() + " sat in");
        this.log.setAction("");
        this.log.setSeat("");
        this.log.setNotification("");
        gambler.setSittingIn(true);
        this.playersSittingInCount++;
        if (!this.gameIsOn && this.playersSittingInCount > 1) {
//            initializeRound(false); //todo init Round game
        }

    }

    public void deleteGamblerOnTable(Gambler gambler) {
        this.seats.remove(gambler);
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
        int seatNumber = gambler.getNumberSeatOnTable();
        if (seatNumber > -1) {
            this.seatsPlace[seatNumber] = null;
            gambler.setNumberSeatOnTable(-1);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        if (id != table.id) return false;
        if (seatsCount != table.seatsCount) return false;
        if (playersSeatedCount != table.playersSeatedCount) return false;
        if (privateTable != table.privateTable) return false;
        if (playersSittingInCount != table.playersSittingInCount) return false;
        if (playersInHandCount != table.playersInHandCount) return false;
        if (gameIsOn != table.gameIsOn) return false;
        if (headsUp != table.headsUp) return false;
        if (dealerSeat != table.dealerSeat) return false;
        if (activeSeat != table.activeSeat) return false;
        if (name != null ? !name.equals(table.name) : table.name != null) return false;
        if (bigBlind != null ? !bigBlind.equals(table.bigBlind) : table.bigBlind != null) return false;
        if (smallBlind != null ? !smallBlind.equals(table.smallBlind) : table.smallBlind != null) return false;
        if (lastPlayerToAct != null ? !lastPlayerToAct.equals(table.lastPlayerToAct) : table.lastPlayerToAct != null)
            return false;
        if (seats != null ? !seats.equals(table.seats) : table.seats != null) return false;
        if (deck != null ? !deck.equals(table.deck) : table.deck != null) return false;
        if (minBuyIn != null ? !minBuyIn.equals(table.minBuyIn) : table.minBuyIn != null) return false;
        if (maxBuyIn != null ? !maxBuyIn.equals(table.maxBuyIn) : table.maxBuyIn != null) return false;
        if (pot != null ? !pot.equals(table.pot) : table.pot != null) return false;
        if (biggestBet != null ? !biggestBet.equals(table.biggestBet) : table.biggestBet != null) return false;
        if (!Arrays.equals(seatsPlace, table.seatsPlace)) return false;
        if (phaseGame != null ? !phaseGame.equals(table.phaseGame) : table.phaseGame != null) return false;
        if (!Arrays.equals(board, table.board)) return false;
        return log != null ? log.equals(table.log) : table.log == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + seatsCount;
        result = 31 * result + playersSeatedCount;
        result = 31 * result + (bigBlind != null ? bigBlind.hashCode() : 0);
        result = 31 * result + (smallBlind != null ? smallBlind.hashCode() : 0);
        result = 31 * result + (privateTable ? 1 : 0);
        result = 31 * result + playersSittingInCount;
        result = 31 * result + playersInHandCount;
        result = 31 * result + (lastPlayerToAct != null ? lastPlayerToAct.hashCode() : 0);
        result = 31 * result + (gameIsOn ? 1 : 0);
        result = 31 * result + (headsUp ? 1 : 0);
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        result = 31 * result + (deck != null ? deck.hashCode() : 0);
        result = 31 * result + (minBuyIn != null ? minBuyIn.hashCode() : 0);
        result = 31 * result + (maxBuyIn != null ? maxBuyIn.hashCode() : 0);
        result = 31 * result + (pot != null ? pot.hashCode() : 0);
        result = 31 * result + (biggestBet != null ? biggestBet.hashCode() : 0);
        result = 31 * result + dealerSeat;
        result = 31 * result + activeSeat;
        result = 31 * result + Arrays.hashCode(seatsPlace);
        result = 31 * result + (phaseGame != null ? phaseGame.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(board);
        result = 31 * result + (log != null ? log.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Table.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("seatsCount=" + seatsCount)
                .add("playersSeatedCount=" + playersSeatedCount)
                .add("bigBlind=" + bigBlind)
                .add("smallBlind=" + smallBlind)
                .add("privateTable=" + privateTable)
                .add("playersSittingInCount=" + playersSittingInCount)
                .add("playersInHandCount=" + playersInHandCount)
                .add("lastPlayerToAct='" + lastPlayerToAct + "'")
                .add("gameIsOn=" + gameIsOn)
                .add("headsUp=" + headsUp)
                .add("seats=" + seats)
                .add("deck=" + deck)
                .add("minBuyIn=" + minBuyIn)
                .add("maxBuyIn=" + maxBuyIn)
                .add("pot=" + pot)
                .add("biggestBet=" + biggestBet)
                .add("dealerSeat=" + dealerSeat)
                .add("activeSeat=" + activeSeat)
                .add("seatsPlace=" + Arrays.toString(seatsPlace))
                .add("phaseGame='" + phaseGame + "'")
                .add("board=" + Arrays.toString(board))
                .add("log=" + log)
                .toString();
    }
}
