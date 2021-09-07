package com.epam.poker.model.logic.service.game;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.entity.game.Game;

import java.util.List;

public interface GameService {
    long add(Game game) throws ServiceException;
    int findGamesAmout() throws ServiceException;
    List<Game> findGamesRange(int offset, int amount) throws ServiceException;
    Game findGameById(long id) throws ServiceException;
}
