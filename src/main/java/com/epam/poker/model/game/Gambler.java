package com.epam.poker.model.game;

import com.epam.poker.model.Entity;
import jakarta.websocket.Session;

import java.math.BigDecimal;
import java.util.Arrays;

public class Gambler implements Entity {
    private String name;
    private BigDecimal moneyInPlay;
    private boolean sittingIn;
    private boolean inHand;
    private boolean hasCards;
    private String[] publicCards;
    private BigDecimal bet;
    private Session session;
    private BigDecimal balance;
    private String titleRoom;
    private int sittingOnTable;
    private int numberSeatOnTable;
    private String[] privateCards;
    private EvaluateHand evaluateHand;
    private String img;

    public Gambler(String name, BigDecimal chipsInPlay, boolean sittingIn, boolean inHand, boolean hasCards,
                   String[] publicCards, BigDecimal bet, Session session, BigDecimal chips, String titleRoom,
                   int seatTable, String[] privateCards, String img) {
        this.name = name;
        this.moneyInPlay = chipsInPlay;
        this.sittingIn = sittingIn;
        this.inHand = inHand;
        this.hasCards = hasCards;
        this.publicCards = publicCards;
        this.bet = BigDecimal.ZERO;
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

    public String[] getPrivateCards() {
        return privateCards;
    }



    public String getName() {
        return name;
    }

    public BigDecimal getMoneyInPlay() {
        return moneyInPlay;
    }

    public boolean isSittingIn() {
        return sittingIn;
    }

    public boolean isInHand() {
        return inHand;
    }

    public boolean isHasCards() {
        return hasCards;
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

    public String getTitleRoom() {
        return titleRoom;
    }

    public EvaluateHand getEvaluateHand() {
        return evaluateHand;
    }

    public String getImg() {
        return img;
    }

    public static GamblerBuilder builder() {
        return new GamblerBuilder();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoneyInPlay(BigDecimal moneyInPlay) {
        this.moneyInPlay = moneyInPlay;
    }

    public void setSittingIn(boolean sittingIn) {
        this.sittingIn = sittingIn;
    }

    public void setInHand(boolean inHand) {
        this.inHand = inHand;
    }

    public void setHasCards(boolean hasCards) {
        this.hasCards = hasCards;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setTitleRoom(String titleRoom) {
        this.titleRoom = titleRoom;
    }

    public void setNumberSeatOnTable(int numberSeatOnTable) {
        this.numberSeatOnTable = numberSeatOnTable;
    }

    public void setPrivateCards(String[] privateCards) {
        this.privateCards = privateCards;
    }

    public void setEvaluateHand(EvaluateHand evaluateHand) {
        this.evaluateHand = evaluateHand;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gambler gambler = (Gambler) o;

        if (sittingIn != gambler.sittingIn) return false;
        if (inHand != gambler.inHand) return false;
        if (hasCards != gambler.hasCards) return false;
        if (sittingOnTable != gambler.sittingOnTable) return false;
        if (numberSeatOnTable != gambler.numberSeatOnTable) return false;
        if (name != null ? !name.equals(gambler.name) : gambler.name != null) return false;
        if (moneyInPlay != null ? !moneyInPlay.equals(gambler.moneyInPlay) : gambler.moneyInPlay != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(publicCards, gambler.publicCards)) return false;
        if (bet != null ? !bet.equals(gambler.bet) : gambler.bet != null) return false;
        if (session != null ? !session.equals(gambler.session) : gambler.session != null) return false;
        if (balance != null ? !balance.equals(gambler.balance) : gambler.balance != null) return false;
        if (titleRoom != null ? !titleRoom.equals(gambler.titleRoom) : gambler.titleRoom != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
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
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (titleRoom != null ? titleRoom.hashCode() : 0);
        result = 31 * result + sittingOnTable;
        result = 31 * result + numberSeatOnTable;
        result = 31 * result + Arrays.hashCode(privateCards);
        result = 31 * result + (evaluateHand != null ? evaluateHand.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Gambler{");
        sb.append("name='").append(name).append('\'');
        sb.append(", moneyInPlay=").append(moneyInPlay);
        sb.append(", sittingIn=").append(sittingIn);
        sb.append(", inHand=").append(inHand);
        sb.append(", hasCards=").append(hasCards);
        sb.append(", publicCards=").append(Arrays.toString(publicCards));
        sb.append(", bet=").append(bet);
        sb.append(", session=").append(session);
        sb.append(", balance=").append(balance);
        sb.append(", titleRoom='").append(titleRoom).append('\'');
        sb.append(", sittingOnTable=").append(sittingOnTable);
        sb.append(", numberSeatOnTable=").append(numberSeatOnTable);
        sb.append(", privateCards=").append(Arrays.toString(privateCards));
        sb.append(", evaluateHand=").append(evaluateHand);
        sb.append(", img='").append(img).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class GamblerBuilder {
        private String name;
        private BigDecimal chipsInPlay;
        private boolean sittingIn;
        private boolean inHand;
        private boolean hasCards;
        private String[] publicCards;
        private BigDecimal bet;
        private Session session;
        private BigDecimal chips;
        private String titleRoom;
        private int seatTable;
        private String[] privateCards;
        private String img;

        public GamblerBuilder setTitleRoom(String titleRoom) {
            this.titleRoom = titleRoom;
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
                    bet, session, chips, titleRoom, seatTable,
                    privateCards, img);
        }
    }
}
