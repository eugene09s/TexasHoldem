package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.GamePlayer;

import java.util.List;

public interface GamePlayerService {
    long add(GamePlayer gamePlayer) throws ServiceException;
    List<GamePlayer> findGamePlayersAll() throws ServiceException;
    int findGamePlayersAmount() throws ServiceException;
    List<GamePlayer> findGamePlayersByGameId(long gameId) throws ServiceException;
    List<GamePlayer> findGamePlayersByUserId(long userId) throws ServiceException;
}
