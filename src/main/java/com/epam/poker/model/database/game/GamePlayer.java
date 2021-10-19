package com.epam.poker.model.database.game;

import com.epam.poker.model.Entity;

public class GamePlayer implements Entity {
    private long gamePlayerId;
    private String lastAction;
    private String twoCards;
    private String combinationsCards;
    private long userId;
    private long gameId;

    public GamePlayer(long gamePlayerId, String lastAction, String twoCards, String combinationsCards, long userId, long gameId) {
        this.gamePlayerId = gamePlayerId;
        this.lastAction = lastAction;
        this.twoCards = twoCards;
        this.combinationsCards = combinationsCards;
        this.userId = userId;
        this.gameId = gameId;
    }

    private GamePlayer() {
    }

    public long getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(long gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getTwoCards() {
        return twoCards;
    }

    public void setTwoCards(String twoCards) {
        this.twoCards = twoCards;
    }

    public String getCombinationsCards() {
        return combinationsCards;
    }

    public void setCombinationsCards(String combinationsCards) {
        this.combinationsCards = combinationsCards;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamePlayer that = (GamePlayer) o;

        if (gamePlayerId != that.gamePlayerId) return false;
        if (userId != that.userId) return false;
        if (gameId != that.gameId) return false;
        if (lastAction != null ? !lastAction.equals(that.lastAction) : that.lastAction != null) return false;
        if (twoCards != null ? !twoCards.equals(that.twoCards) : that.twoCards != null) return false;
        return combinationsCards != null ? combinationsCards.equals(that.combinationsCards) : that.combinationsCards == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (gamePlayerId ^ (gamePlayerId >>> 32));
        result = 31 * result + (lastAction != null ? lastAction.hashCode() : 0);
        result = 31 * result + (twoCards != null ? twoCards.hashCode() : 0);
        result = 31 * result + (combinationsCards != null ? combinationsCards.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (gameId ^ (gameId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GamePlayer{");
        sb.append("gamePlayerId=").append(gamePlayerId);
        sb.append(", lastAction='").append(lastAction).append('\'');
        sb.append(", twoCards='").append(twoCards).append('\'');
        sb.append(", combinationsCards='").append(combinationsCards).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", gameId=").append(gameId);
        sb.append('}');
        return sb.toString();
    }

    public static GamePlayerBuilder builder() {
        return new GamePlayerBuilder();
    }

    public static class GamePlayerBuilder {
        private final GamePlayer gamePlayer;

        public GamePlayerBuilder() {
            gamePlayer = new GamePlayer();
        }

        public GamePlayerBuilder setGamePlayerId(long gamePlayerId) {
            gamePlayer.setGamePlayerId(gamePlayerId);
            return this;
        }

        public GamePlayerBuilder setLastAction(String lastAction) {
            gamePlayer.setLastAction(lastAction);
            return this;
        }

        public GamePlayerBuilder setTwoCards(String twoCards) {
            gamePlayer.setTwoCards(twoCards);
            return this;
        }

        public GamePlayerBuilder setCombinationsCards(String combinationsCards) {
            gamePlayer.setCombinationsCards(combinationsCards);
            return this;
        }

        public GamePlayerBuilder setUserId(long userId) {
            gamePlayer.setUserId(userId);
            return this;
        }

        public GamePlayerBuilder setGameId(long gameId) {
            gamePlayer.setGameId(gameId);
            return this;
        }

        public GamePlayer createGamePlayer() {
            return gamePlayer;
        }
    }
}
