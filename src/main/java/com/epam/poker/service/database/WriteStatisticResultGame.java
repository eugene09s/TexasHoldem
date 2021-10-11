package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dto.StatisticResultGame;

public interface WriteStatisticResultGame {
    long execute(StatisticResultGame statisticResultGame) throws ServiceException;
}
