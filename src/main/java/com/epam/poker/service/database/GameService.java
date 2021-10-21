package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.Game;

import java.util.List;

/**
 * The interface Game service which processing statistic game.
 */
public interface GameService {
    /**
     * Add new Game in database.
     *
     * @param game the game
     * @return the id game
     * @throws ServiceException a wrapper for lower errors.
     */
    long add(Game game) throws ServiceException;

    /**
     * Find games amount in database.
     *
     * @return the amount of games
     * @throws ServiceException a wrapper for lower errors.
     */
    int findGamesAmount() throws ServiceException;

    /**
     * Find games of range list in database. Get games.
     *
     * @param offset the offset in database
     * @param amount the amount of games
     * @return the list
     * @throws ServiceException a wrapper for lower errors.
     */
    List<Game> findGamesRange(int offset, int amount) throws ServiceException;

    /**
     * Find game by id in database and get it.
     *
     * @param id the id of game
     * @return the game
     * @throws ServiceException a wrapper for lower errors.
     */
    Game findGameById(long id) throws ServiceException;

    /**
     * Find all list from database.
     *
     * @return the list of games
     * @throws ServiceException a wrapper for lower errors.
     */
    List<Game> findAll() throws ServiceException;
}
