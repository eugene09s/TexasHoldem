package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.game.GamePlayer;

import java.util.List;

public interface GamePlayerDao extends Dao<GamePlayer> {
    /**
     * Gets game player amount in database.
     *
     * @return  an amount of all game players.
     *
     * @throws DaoException  if database errors occurs.
     */
    int findGamePLayerAmount() throws DaoException;
    long add(GamePlayer gamePlayer) throws DaoException;
    List<GamePlayer> findGamePlayersByGameId(long gameId) throws DaoException;
    List<GamePlayer> findGamePlayersByUserId(long userId) throws DaoException;
}
