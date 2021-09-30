package com.epam.poker.game.entity;

import jakarta.websocket.Session;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.StringJoiner;

public class Gambler {
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
    private boolean sittingOnTable;
    private int seatTable;
    private String[] privateCards;
    private String evaluateHand;
    private String img;

    public Gambler(String name, BigDecimal chipsInPlay, boolean sittingIn, boolean inHand, boolean hasCards,
                   String[] publicCards, BigDecimal bet, int tableId, Session session, BigDecimal chips, String titleRoom,
                   boolean sittingOnTable, int seatTable, String[] privateCards, String evaluateHand, String img) {
        this.name = name;
        this.chipsInPlay = chipsInPlay;
        this.sittingIn = sittingIn;
        this.inHand = inHand;
        this.hasCards = hasCards;
        this.publicCards = publicCards;
        this.bet = bet;
        this.tableId = tableId;
        this.session = session;
        this.chips = chips;
        this.titleRoom = titleRoom;
        this.sittingOnTable = sittingOnTable;
        this.seatTable = seatTable;
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

    public BigDecimal getChips() {
        return chips;
    }

    public Gambler setChips(BigDecimal chips) {
        this.chips = chips;
        return this;
    }

    public boolean isSittingOnTable() {
        return sittingOnTable;
    }

    public Gambler setSittingOnTable(boolean sittingOnTable) {
        this.sittingOnTable = sittingOnTable;
        return this;
    }

    public int getSeatTable() {
        return seatTable;
    }

    public Gambler setSeatTable(int seatTable) {
        this.seatTable = seatTable;
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

    public BigDecimal getChipsInPlay() {
        return chipsInPlay;
    }

    public Gambler setChipsInPlay(BigDecimal chipsInPlay) {
        this.chipsInPlay = chipsInPlay;
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
        if (seatTable != gambler.seatTable) return false;
        if (name != null ? !name.equals(gambler.name) : gambler.name != null) return false;
        if (chipsInPlay != null ? !chipsInPlay.equals(gambler.chipsInPlay) : gambler.chipsInPlay != null) return false;
        if (!Arrays.equals(publicCards, gambler.publicCards)) return false;
        if (bet != null ? !bet.equals(gambler.bet) : gambler.bet != null) return false;
        if (session != null ? !session.equals(gambler.session) : gambler.session != null) return false;
        if (chips != null ? !chips.equals(gambler.chips) : gambler.chips != null) return false;
        if (titleRoom != null ? !titleRoom.equals(gambler.titleRoom) : gambler.titleRoom != null) return false;
        if (!Arrays.equals(privateCards, gambler.privateCards)) return false;
        if (evaluateHand != null ? !evaluateHand.equals(gambler.evaluateHand) : gambler.evaluateHand != null)
            return false;
        return img != null ? img.equals(gambler.img) : gambler.img == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (chipsInPlay != null ? chipsInPlay.hashCode() : 0);
        result = 31 * result + (sittingIn ? 1 : 0);
        result = 31 * result + (inHand ? 1 : 0);
        result = 31 * result + (hasCards ? 1 : 0);
        result = 31 * result + Arrays.hashCode(publicCards);
        result = 31 * result + (bet != null ? bet.hashCode() : 0);
        result = 31 * result + tableId;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (chips != null ? chips.hashCode() : 0);
        result = 31 * result + (titleRoom != null ? titleRoom.hashCode() : 0);
        result = 31 * result + (sittingOnTable ? 1 : 0);
        result = 31 * result + seatTable;
        result = 31 * result + Arrays.hashCode(privateCards);
        result = 31 * result + (evaluateHand != null ? evaluateHand.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Gambler.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("chipsInPlay=" + chipsInPlay)
                .add("sittingIn=" + sittingIn)
                .add("inHand=" + inHand)
                .add("hasCards=" + hasCards)
                .add("publicCards=" + Arrays.toString(publicCards))
                .add("bet=" + bet)
                .add("tableId=" + tableId)
                .add("session=" + session)
                .add("chips=" + chips)
                .add("titleRoom='" + titleRoom + "'")
                .add("sittingOnTable=" + sittingOnTable)
                .add("seatTable=" + seatTable)
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
        private boolean sittingOnTable;
        private int seatTable;
        private String[] privateCards;
        private String evaluateHand;
        private String img;

        public GamblerBuilder setTitleRoom(String titleRoom) {
            this.titleRoom = titleRoom;
            return this;
        }

        public GamblerBuilder setSittingOnTable(boolean sittingOnTable) {
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
                    bet, tableId, session, chips, titleRoom, sittingOnTable, seatTable,
                    privateCards, evaluateHand, img);
        }
    }
}
