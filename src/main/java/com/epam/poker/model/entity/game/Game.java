package com.epam.poker.model.entity.game;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.StringJoiner;

public class Game {
    private long gameId;
    private String title;
    private Timestamp timestamp;
    private BigDecimal bank;
    private String fiveCards;

    public Game(long gameId, String title, Timestamp timestamp, BigDecimal bank, String fiveCards) {
        this.gameId = gameId;
        this.title = title;
        this.timestamp = timestamp;
        this.bank = bank;
        this.fiveCards = fiveCards;
    }

    private Game() {
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getBank() {
        return bank;
    }

    public void setBank(BigDecimal bank) {
        this.bank = bank;
    }

    public String getFiveCards() {
        return fiveCards;
    }

    public void setFiveCards(String fiveCards) {
        this.fiveCards = fiveCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (gameId != game.gameId) return false;
        if (title != null ? !title.equals(game.title) : game.title != null) return false;
        if (timestamp != null ? !timestamp.equals(game.timestamp) : game.timestamp != null) return false;
        if (bank != null ? !bank.equals(game.bank) : game.bank != null) return false;
        return fiveCards != null ? fiveCards.equals(game.fiveCards) : game.fiveCards == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (fiveCards != null ? fiveCards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Game.class.getSimpleName() + "[", "]")
                .add("gameId=" + gameId)
                .add("title='" + title + "'")
                .add("timestamp=" + timestamp)
                .add("bank=" + bank)
                .add("fiveCards='" + fiveCards + "'")
                .toString();
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static class GameBuilder {
        private Game game;

        public GameBuilder() {
            game = new Game();
        }

        public GameBuilder setGameId(long gameId) {
            game.setGameId(gameId);
            return this;
        }

        public GameBuilder setTitle(String title) {
            game.setTitle(title);
            return this;
        }

        public GameBuilder setTimestamp(Timestamp timestamp) {
            game.setTimestamp(timestamp);
            return this;
        }

        public GameBuilder setBank(BigDecimal bank) {
            game.setBank(bank);
            return this;
        }

        public GameBuilder setFiveCards(String fiveCards) {
            game.setFiveCards(fiveCards);
            return this;
        }

        public Game createGame() {
            return new Game();
        }
    }
}
