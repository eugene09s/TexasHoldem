package com.epam.poker.model.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.entity.database.game.GameWinner;

import java.util.List;

public interface GameWinnerDao extends Dao<GameWinner> {
    /**
     * Gets game winner amount in database.
     *
     * @return  an amount of all game winner.
     *
     * @throws DaoException  if database errors occurs.
     */
    int findGameWinnerAmount() throws DaoException;
    long add(GameWinner gameWinner) throws DaoException;
    List<GameWinner> findGameWinnerByGameId(long gameId) throws DaoException;
    List<GameWinner> findGameWinnerByUserId(long userId) throws DaoException;
}
