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
    private String[] cards;
    private BigDecimal bet;
    private int tableId;
    private Session session;
    private String titleRoom;
    private String evaluateHand;
    private String img;

    private Gambler(String name, BigDecimal chipsInPlay,
                    boolean sittingIn, boolean inHand,
                    boolean hasCards, String[] cards,
                    BigDecimal bet, Session session,
                    String nameRoom, String evaluateHand,
                    String img, int tableId) {
        this.name = name;
        this.chipsInPlay = chipsInPlay;
        this.sittingIn = sittingIn;
        this.inHand = inHand;
        this.hasCards = hasCards;
        this.cards = cards;
        this.bet = bet;
        this.session = session;
        this.titleRoom = nameRoom;
        this.evaluateHand = evaluateHand;
        this.img = img;
        this.tableId = tableId;
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

    public String[] getCards() {
        return cards;
    }

    public Gambler setCards(String[] cards) {
        this.cards = cards;
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
        if (name != null ? !name.equals(gambler.name) : gambler.name != null) return false;
        if (chipsInPlay != null ? !chipsInPlay.equals(gambler.chipsInPlay) : gambler.chipsInPlay != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(cards, gambler.cards)) return false;
        if (bet != null ? !bet.equals(gambler.bet) : gambler.bet != null) return false;
        if (session != null ? !session.equals(gambler.session) : gambler.session != null) return false;
        if (titleRoom != null ? !titleRoom.equals(gambler.titleRoom) : gambler.titleRoom != null) return false;
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
        result = 31 * result + Arrays.hashCode(cards);
        result = 31 * result + (bet != null ? bet.hashCode() : 0);
        result = 31 * result + tableId;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (titleRoom != null ? titleRoom.hashCode() : 0);
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
                .add("cards=" + Arrays.toString(cards))
                .add("bet=" + bet)
                .add("tableId=" + tableId)
                .add("titleRoom='" + titleRoom + "'")
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
        private String[] cards;
        private BigDecimal bet;
        private Session session;
        private String nameRoom;
        private String evaluateHand;
        private String img;
        private int tableId;

        public GamblerBuilder setEvaluateHand(String evaluateHand) {
            this.evaluateHand = evaluateHand;
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

        public GamblerBuilder setCards(String[] cards) {
            this.cards = cards;
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

        public GamblerBuilder setNameRoom(String nameRoom) {
            this.nameRoom = nameRoom;
            return this;
        }

        public Gambler createGambler() {
            return new Gambler(name, chipsInPlay, sittingIn, inHand,
                    hasCards, cards, bet, session, nameRoom, evaluateHand,
                    img, tableId);
        }
    }
}
