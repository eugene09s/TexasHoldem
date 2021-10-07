package com.epam.poker.model.entity.database.game;

import com.epam.poker.model.entity.Entity;

import java.util.StringJoiner;

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

    public GameWinner setGameWinnerId(long gameWinnerId) {
        this.gameWinnerId = gameWinnerId;
        return this;
    }

    public long getGameId() {
        return gameId;
    }

    public GameWinner setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public GameWinner setUserId(long userId) {
        this.userId = userId;
        return this;
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
        return new StringJoiner(", ", GameWinner.class.getSimpleName() + "[", "]")
                .add("gameWinnerId=" + gameWinnerId)
                .add("gameId=" + gameId)
                .add("UserId=" + userId)
                .toString();
    }

    public static GameWinnerBuilder builder() {
        return new GameWinnerBuilder();
    }

    public static class GameWinnerBuilder {
        private GameWinner gameWinner;

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
