package com.epam.poker.game.entity;

import jakarta.websocket.Session;

import java.math.BigDecimal;

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
    private int sittingOnTable;
    private int numberSeatOnTable;
    private String[] privateCards;
    private EvaluateHand evaluateHand;
    private String img;

    public Gambler(String name, BigDecimal chipsInPlay, boolean sittingIn, boolean inHand, boolean hasCards,
                   String[] publicCards, BigDecimal bet, int tableId, Session session, BigDecimal chips, String titleRoom,
                   int seatTable, String[] privateCards, String img) {
        this.name = name;
        this.moneyInPlay = chipsInPlay;
        this.sittingIn = sittingIn;
        this.inHand = inHand;
        this.hasCards = hasCards;
        this.publicCards = publicCards;
        this.bet = BigDecimal.ZERO;
        this.tableId = tableId;
        this.session = session;
        this.balance = chips;
        this.titleRoom = titleRoom;
        this.sittingOnTable = -1;
        this.numberSeatOnTable = seatTable;
        this.privateCards = privateCards;
        this.evaluateHand = new EvaluateHand();
        this.img = img;
    }

    public String[] getPublicCards() {
        return publicCards;
    }

    public Gambler setPublicCards(String[] publicCards) {
        this.publicCards = publicCards;
        return this;
    }

    public void sitOut() {
        if (this.sittingOnTable > -1) {
            this.sittingIn = false;
            this.inHand = false;
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Gambler setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public int getSittingOnTable() {
        return sittingOnTable;
    }

    public Gambler setSittingOnTable(int sittingOnTable) {
        this.sittingOnTable = sittingOnTable;
        return this;
    }

    public void bet(BigDecimal amount) {
        if (amount.compareTo(this.moneyInPlay) > 0) {
            amount = this.moneyInPlay;
        }
        this.moneyInPlay = this.moneyInPlay.subtract(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            amount  = amount.multiply(new BigDecimal(-1));
        }
        this.bet = this.bet.add(amount);
    }

    public void raise(BigDecimal amount) {
        if (this.moneyInPlay.compareTo(amount) < 0) {
            amount = this.moneyInPlay;
        }
        this.moneyInPlay = this.moneyInPlay.subtract(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            amount  = amount.multiply(new BigDecimal(-1));
        }
        this.bet = this.bet.add(amount);
    }

    public int getNumberSeatOnTable() {
        return numberSeatOnTable;
    }

    public Gambler setNumberSeatOnTable(int numberSeatOnTable) {
        this.numberSeatOnTable = numberSeatOnTable;
        return this;
    }

    public void leaveTable() {
        if (this.sittingOnTable > -1) {
            sitOut();
            this.balance = this.balance.add(this.moneyInPlay);
            this.moneyInPlay = BigDecimal.ZERO;
            this.sittingOnTable = -1;
            this.numberSeatOnTable = -1;
        }
    }

    public void plusMoneyInTheGame(BigDecimal money) {
        this.moneyInPlay = this.moneyInPlay.add(money);
    }

//fixme should be another methods set
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

    public EvaluateHand getEvaluateHand() {
        return evaluateHand;
    }

    public Gambler setEvaluateHand(EvaluateHand evaluateHand) {
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
        private EvaluateHand evaluateHand;
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

        public GamblerBuilder setEvaluateHand(EvaluateHand evaluateHand) {
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
                    privateCards, img);
        }
    }
}
