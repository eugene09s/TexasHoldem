package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dto.StatisticResultGame;

import java.util.List;

/**
 * The interface Statistic of game service, doing report of game.
 */
public interface StatisticOfGameService {
    /**
     * Push data statistic last game.
     *
     * @param statisticResultGame the statistic result game
     * @return the long of id Game from database
     * @throws ServiceException a wrapper for lower errors.
     */
    long pushData(StatisticResultGame statisticResultGame) throws ServiceException;

    /**
     * Fetch data by range from database.
     *
     * @param offset the offset in database
     * @param amount the amount games
     * @return the list of statistic games
     * @throws ServiceException a wrapper for lower errors.
     */
    List<StatisticResultGame> fetchDataByRange(int offset, int amount) throws ServiceException;
}
