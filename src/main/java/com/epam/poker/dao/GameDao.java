package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.game.Game;

import java.util.List;

/**
 * The interface Game dao.
 */
public interface GameDao extends Dao<Game> {
    /**
     * Add long.
     *
     * @param game the game
     * @return the long
     * @throws DaoException the dao exception
     */
    long add(Game game) throws DaoException;

    /**
     * Gets games amount in database.
     *
     * @return an amount of all games.
     * @throws DaoException if database errors occurs.
     */
    int findGameAmount() throws DaoException;

    /**
     * Gets list games in range described as offset and amount of games.
     *
     * @param offset an amount of games to get.
     * @param amount the amount
     * @return a received list of games.
     * @throws DaoException if database errors occurs.
     */
    List<Game> findGamesRange(int offset, int amount) throws DaoException;
}
