package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dto.StatisticResultGame;

import java.util.List;

public interface StatisticOfGameService {
    long pushData(StatisticResultGame statisticResultGame) throws ServiceException;
    List<StatisticResultGame> fetchDataByRange(int offset, int amount) throws ServiceException;
}
