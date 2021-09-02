package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.entity.game.Game;

public interface GameDao extends Dao<Game> {
    long add(Game game) throws DaoException;
    /**
     * Gets games amount in database.
     *
     * @return  an amount of all games.
     *
     * @throws DaoException  if database errors occurs.
     */
    int findGameAmount() throws DaoException;
}
