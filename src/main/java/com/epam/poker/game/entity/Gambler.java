package com.epam.poker.game.entity;

import jakarta.websocket.Session;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.StringJoiner;

public class Gambler {
    private String name;
    private BigDecimal moneyInPlay;
    private boolean sittingIn;
    private boolean inHand;
    private boolean hasCards;
    private String[] publicCards;
    private BigDecimal bet;
    private int tableId;
    private Session session;
    private BigDecimal balance;
    private String titleRoom;
    private long sittingOnTable;
    private int numberSeatOnTable;
    private String[] privateCards;
    private String evaluateHand;
    private String img;

    public Gambler(String name, BigDecimal chipsInPlay, boolean sittingIn, boolean inHand, boolean hasCards,
                   String[] publicCards, BigDecimal bet, int tableId, Session session, BigDecimal chips, String titleRoom,
                   int seatTable, String[] privateCards, String evaluateHand, String img) {
        this.name = name;
        this.moneyInPlay = chipsInPlay;
        this.sittingIn = sittingIn;
        this.inHand = inHand;
        this.hasCards = hasCards;
        this.publicCards = publicCards;
        this.bet = bet;
        this.tableId = tableId;
        this.session = session;
        this.balance = chips;
        this.titleRoom = titleRoom;
        this.sittingOnTable = -1;
        this.numberSeatOnTable = seatTable;
        this.privateCards = privateCards;
        this.evaluateHand = evaluateHand;
        this.img = img;
    }

    public String[] getPublicCards() {
        return publicCards;
    }

    public Gambler setPublicCards(String[] publicCards) {
        this.publicCards = publicCards;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Gambler setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public long getSittingOnTable() {
        return sittingOnTable;
    }

    public Gambler setSittingOnTable(long sittingOnTable) {
        this.sittingOnTable = sittingOnTable;
        return this;
    }

    public int getNumberSeatOnTable() {
        return numberSeatOnTable;
    }

    public Gambler setNumberSeatOnTable(int numberSeatOnTable) {
        this.numberSeatOnTable = numberSeatOnTable;
        return this;
    }

    public String[] getPrivateCards() {
        return privateCards;
    }

    public Gambler setPrivateCards(String[] privateCards) {
        this.privateCards = privateCards;
        return this;
    }

    public int getTableId() {
        return tableId;
    }

    public Gambler setTableId(int tableId) {
        this.tableId = tableId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Gambler setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getMoneyInPlay() {
        return moneyInPlay;
    }

    public Gambler setMoneyInPlay(BigDecimal moneyInPlay) {
        this.moneyInPlay = moneyInPlay;
        return this;
    }

    public boolean isSittingIn() {
        return sittingIn;
    }

    public Gambler setSittingIn(boolean sittingIn) {
        this.sittingIn = sittingIn;
        return this;
    }

    public boolean isInHand() {
        return inHand;
    }

    public Gambler setInHand(boolean inHand) {
        this.inHand = inHand;
        return this;
    }

    public boolean isHasCards() {
        return hasCards;
    }

    public Gambler setHasCards(boolean hasCards) {
        this.hasCards = hasCards;
        return this;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public Gambler setBet(BigDecimal bet) {
        this.bet = bet;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public Gambler setSession(Session session) {
        this.session = session;
        return this;
    }

    public String getTitleRoom() {
        return titleRoom;
    }

    public Gambler setTitleRoom(String titleRoom) {
        this.titleRoom = titleRoom;
        return this;
    }

    public String getEvaluateHand() {
        return evaluateHand;
    }

    public Gambler setEvaluateHand(String evaluateHand) {
        this.evaluateHand = evaluateHand;
        return this;
    }

    public String getImg() {
        return img;
    }

    public Gambler setImg(String img) {
        this.img = img;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gambler gambler = (Gambler) o;

        if (sittingIn != gambler.sittingIn) return false;
        if (inHand != gambler.inHand) return false;
        if (hasCards != gambler.hasCards) return false;
        if (tableId != gambler.tableId) return false;
        if (sittingOnTable != gambler.sittingOnTable) return false;
        if (numberSeatOnTable != gambler.numberSeatOnTable) return false;
        if (name != null ? !name.equals(gambler.name) : gambler.name != null) return false;
        if (moneyInPlay != null ? !moneyInPlay.equals(gambler.moneyInPlay) : gambler.moneyInPlay != null) return false;
        if (!Arrays.equals(publicCards, gambler.publicCards)) return false;
        if (bet != null ? !bet.equals(gambler.bet) : gambler.bet != null) return false;
        if (session != null ? !session.equals(gambler.session) : gambler.session != null) return false;
        if (balance != null ? !balance.equals(gambler.balance) : gambler.balance != null) return false;
        if (titleRoom != null ? !titleRoom.equals(gambler.titleRoom) : gambler.titleRoom != null) return false;
        if (!Arrays.equals(privateCards, gambler.privateCards)) return false;
        if (evaluateHand != null ? !evaluateHand.equals(gambler.evaluateHand) : gambler.evaluateHand != null)
            return false;
        return img != null ? img.equals(gambler.img) : gambler.img == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (moneyInPlay != null ? moneyInPlay.hashCode() : 0);
        result = 31 * result + (sittingIn ? 1 : 0);
        result = 31 * result + (inHand ? 1 : 0);
        result = 31 * result + (hasCards ? 1 : 0);
        result = 31 * result + Arrays.hashCode(publicCards);
        result = 31 * result + (bet != null ? bet.hashCode() : 0);
        result = 31 * result + tableId;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (titleRoom != null ? titleRoom.hashCode() : 0);
        result = 31 * result + numberSeatOnTable;
        result = 31 * result + Arrays.hashCode(privateCards);
        result = 31 * result + (evaluateHand != null ? evaluateHand.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Gambler.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("chipsInPlay=" + moneyInPlay)
                .add("sittingIn=" + sittingIn)
                .add("inHand=" + inHand)
                .add("hasCards=" + hasCards)
                .add("publicCards=" + Arrays.toString(publicCards))
                .add("bet=" + bet)
                .add("tableId=" + tableId)
                .add("session=" + session)
                .add("chips=" + balance)
                .add("titleRoom='" + titleRoom + "'")
                .add("sittingOnTable=" + sittingOnTable)
                .add("seatTable=" + numberSeatOnTable)
                .add("privateCards=" + Arrays.toString(privateCards))
                .add("evaluateHand='" + evaluateHand + "'")
                .add("img='" + img + "'")
                .toString();
    }

    public static GamblerBuilder builder() {
        return new GamblerBuilder();
    }

    public static class GamblerBuilder {
        private String name;
        private BigDecimal chipsInPlay;
        private boolean sittingIn;
        private boolean inHand;
        private boolean hasCards;
        private String[] publicCards;
        private BigDecimal bet;
        private int tableId;
        private Session session;
        private BigDecimal chips;
        private String titleRoom;
        private long sittingOnTable;
        private int seatTable;
        private String[] privateCards;
        private String evaluateHand;
        private String img;

        public GamblerBuilder setTitleRoom(String titleRoom) {
            this.titleRoom = titleRoom;
            return this;
        }

        public GamblerBuilder setSittingOnTable(long sittingOnTable) {
            this.sittingOnTable = sittingOnTable;
            return this;
        }

        public GamblerBuilder setSeatTable(int seatTable) {
            this.seatTable = seatTable;
            return this;
        }

        public GamblerBuilder setPrivateCards(String[] privateCards) {
            this.privateCards = privateCards;
            return this;
        }

        public GamblerBuilder setEvaluateHand(String evaluateHand) {
            this.evaluateHand = evaluateHand;
            return this;
        }

        public GamblerBuilder setPublicCards(String[] publicCards) {
            this.publicCards = publicCards;
            return this;
        }

        public GamblerBuilder setChips(BigDecimal chips) {
            this.chips = chips;
            return this;
        }

        public GamblerBuilder setImg(String img) {
            this.img = img;
            return this;
        }

        public GamblerBuilder setTableId(int tableId) {
            this.tableId = tableId;
            return this;
        }

        public GamblerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public GamblerBuilder setChipsInPlay(BigDecimal chipsInPlay) {
            this.chipsInPlay = chipsInPlay;
            return this;
        }

        public GamblerBuilder setSittingIn(boolean sittingIn) {
            this.sittingIn = sittingIn;
            return this;
        }

        public GamblerBuilder setInHand(boolean inHand) {
            this.inHand = inHand;
            return this;
        }

        public GamblerBuilder setHasCards(boolean hasCards) {
            this.hasCards = hasCards;
            return this;
        }

        public GamblerBuilder setBet(BigDecimal bet) {
            this.bet = bet;
            return this;
        }

        public GamblerBuilder setSession(Session session) {
            this.session = session;
            return this;
        }

        public Gambler createGambler() {
            return new Gambler(name, chipsInPlay, sittingIn, inHand, hasCards, publicCards,
                    bet, tableId, session, chips, titleRoom, seatTable,
                    privateCards, evaluateHand, img);
        }
    }
}
