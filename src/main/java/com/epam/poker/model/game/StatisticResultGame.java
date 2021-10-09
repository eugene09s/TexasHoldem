package com.epam.poker.model.game;

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

    public StatisticResultGame setGamePlayers(List<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
        return this;
    }

    public List<GameWinner> getGameWinners() {
        return gameWinners;
    }

    public StatisticResultGame setGameWinners(List<GameWinner> gameWinners) {
        this.gameWinners = gameWinners;
        return this;
    }
}
