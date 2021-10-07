package com.epam.poker.model.service.database;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.entity.database.game.Game;

import java.util.List;

public interface GameService {
    long add(Game game) throws ServiceException, DaoException;
    int findGamesAmout() throws ServiceException, DaoException;
    List<Game> findGamesRange(int offset, int amount) throws ServiceException, DaoException;
    Game findGameById(long id) throws ServiceException, DaoException;
}
