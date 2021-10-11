package com.epam.poker.model.dto;

import com.epam.poker.model.database.game.Game;
import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.model.database.game.GameWinner;

import java.util.ArrayList;
import java.util.List;

public class StatisticResultGame {
    private Game game;
    private List<GamePlayer> gamePlayers;
    private List<GameWinner> gameWinners;

    public StatisticResultGame() {
        this.gamePlayers = new ArrayList<>();
        this.gameWinners = new ArrayList<>(1);
    }

    public Game getGame() {
        return game;
    }

    public StatisticResultGame setGame(Game game) {
        this.game = game;
        return this;
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public List<GameWinner> getGameWinners() {
        return gameWinners;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public void setGameWinners(List<GameWinner> gameWinners) {
        this.gameWinners = gameWinners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticResultGame that = (StatisticResultGame) o;

        if (game != null ? !game.equals(that.game) : that.game != null) return false;
        if (gamePlayers != null ? !gamePlayers.equals(that.gamePlayers) : that.gamePlayers != null) return false;
        return gameWinners != null ? gameWinners.equals(that.gameWinners) : that.gameWinners == null;
    }

    @Override
    public int hashCode() {
        int result = game != null ? game.hashCode() : 0;
        result = 31 * result + (gamePlayers != null ? gamePlayers.hashCode() : 0);
        result = 31 * result + (gameWinners != null ? gameWinners.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatisticResultGame{");
        sb.append("game=").append(game);
        sb.append(", gamePlayers=").append(gamePlayers);
        sb.append(", gameWinners=").append(gameWinners);
        sb.append('}');
        return sb.toString();
    }
}
