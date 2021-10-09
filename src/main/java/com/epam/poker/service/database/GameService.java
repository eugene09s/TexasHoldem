package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.Game;

import java.util.List;

public interface GameService {
    long add(Game game) throws ServiceException;
    int findGamesAmount() throws ServiceException;
    List<Game> findGamesRange(int offset, int amount) throws ServiceException;
    Game findGameById(long id) throws ServiceException;
    List<Game> findAll() throws ServiceException;
}
