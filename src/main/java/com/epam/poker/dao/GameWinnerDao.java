package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.game.GameWinner;

import java.util.List;

/**
 * The interface Game winner dao.
 */
public interface GameWinnerDao extends Dao<GameWinner> {
    /**
     * Gets game winner amount in database.
     *
     * @return an amount of all game winner.
     * @throws DaoException if database errors occurs.
     */
    int findGameWinnerAmount() throws DaoException;

    /**
     * Add long.
     *
     * @param gameWinner the game winner
     * @return the long
     * @throws DaoException the dao exception
     */
    long add(GameWinner gameWinner) throws DaoException;

    /**
     * Find game winners by game id list.
     *
     * @param gameId the game id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GameWinner> findGameWinnersByGameId(long gameId) throws DaoException;

    /**
     * Find game winner by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GameWinner> findGameWinnerByUserId(long userId) throws DaoException;
}
