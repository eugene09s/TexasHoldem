package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.entity.game.GamePlayer;

import java.util.List;
import java.util.Optional;

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
    List<GamePlayer> findGamePlayerByGameId(long gameId) throws DaoException;
    List<GamePlayer> findGamePlayerByUserId(long userId) throws DaoException;

}
