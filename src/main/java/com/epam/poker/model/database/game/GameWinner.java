package com.epam.poker.model.database.game;

import com.epam.poker.model.Entity;

public class GameWinner implements Entity {
    private long gameWinnerId;
    private long gameId;
    private long userId;

    public GameWinner(long gameWinnerId, long gameId, long userId) {
        this.gameWinnerId = gameWinnerId;
        this.gameId = gameId;
        this.userId = userId;
    }

    private GameWinner() {
    }

    public long getGameWinnerId() {
        return gameWinnerId;
    }

    public void setGameWinnerId(long gameWinnerId) {
        this.gameWinnerId = gameWinnerId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameWinner that = (GameWinner) o;

        if (gameWinnerId != that.gameWinnerId) return false;
        if (gameId != that.gameId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = (int) (gameWinnerId ^ (gameWinnerId >>> 32));
        result = 31 * result + (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameWinner{");
        sb.append("gameWinnerId=").append(gameWinnerId);
        sb.append(", gameId=").append(gameId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }

    public static GameWinnerBuilder builder() {
        return new GameWinnerBuilder();
    }

    public static class GameWinnerBuilder {
        private final GameWinner gameWinner;

        public GameWinnerBuilder() {
            gameWinner = new GameWinner();
        }

        public GameWinnerBuilder setGameWinnerId(long gameWinnerId) {
            gameWinner.setGameWinnerId(gameWinnerId);
            return this;
        }

        public GameWinnerBuilder setGameId(long gameId) {
            gameWinner.setGameId(gameId);
            return this;
        }

        public GameWinnerBuilder setUserId(long userId) {
            gameWinner.setUserId(userId);
            return this;
        }

        public GameWinner createGameWinner() {
            return gameWinner;
        }
    }
}
