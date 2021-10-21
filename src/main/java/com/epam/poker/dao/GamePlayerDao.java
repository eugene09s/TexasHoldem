package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.game.GamePlayer;

import java.util.List;

/**
 * The interface Game player dao.
 */
public interface GamePlayerDao extends Dao<GamePlayer> {
    /**
     * Gets game player amount in database.
     *
     * @return an amount of all game players.
     * @throws DaoException if database errors occurs.
     */
    int findGamePLayerAmount() throws DaoException;

    /**
     * Add long.
     *
     * @param gamePlayer the game player
     * @return the long
     * @throws DaoException the dao exception
     */
    long add(GamePlayer gamePlayer) throws DaoException;

    /**
     * Find game players by game id list.
     *
     * @param gameId the game id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GamePlayer> findGamePlayersByGameId(long gameId) throws DaoException;

    /**
     * Find game players by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<GamePlayer> findGamePlayersByUserId(long userId) throws DaoException;
}
