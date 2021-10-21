package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.GamePlayer;

import java.util.List;

/**
 * The interface Game player service.
 */
public interface GamePlayerService {
    /**
     * Add new GamePlayer and get id game player.
     *
     * @param gamePlayer the game player
     * @return the id new game player
     * @throws ServiceException a wrapper for lower errors.
     */
    long add(GamePlayer gamePlayer) throws ServiceException;

    /**
     * Find game players all from database.
     *
     * @return the list of game players
     * @throws ServiceException a wrapper for lower errors.
     */
    List<GamePlayer> findGamePlayersAll() throws ServiceException;

    /**
     * Find game players amount in database.
     *
     * @return the amount of game players in database
     * @throws ServiceException a wrapper for lower errors.
     */
    int findGamePlayersAmount() throws ServiceException;

    /**
     * Find game players by game id from database.
     *
     * @param gameId the game id
     * @return the list of game player from current game
     * @throws ServiceException a wrapper for lower errors.
     */
    List<GamePlayer> findGamePlayersByGameId(long gameId) throws ServiceException;

    /**
     * Find game players by user id from database
     *
     * @param userId the user id
     * @return the list of game players
     * @throws ServiceException a wrapper for lower errors.
     */
    List<GamePlayer> findGamePlayersByUserId(long userId) throws ServiceException;
}
