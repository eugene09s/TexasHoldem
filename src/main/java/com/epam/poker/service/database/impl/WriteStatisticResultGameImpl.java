package com.epam.poker.service.database.impl;

import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.GameDao;
import com.epam.poker.dao.GamePlayerDao;
import com.epam.poker.dao.GameWinnerDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.impl.game.GameDaoImpl;
import com.epam.poker.dao.impl.game.GamePlayerDaoImpl;
import com.epam.poker.dao.impl.game.GameWinnerDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.model.database.game.GameWinner;
import com.epam.poker.model.dto.StatisticResultGame;
import com.epam.poker.service.database.WriteStatisticResultGame;
import com.epam.poker.service.validator.impl.GamePlayerValidator;
import com.epam.poker.service.validator.impl.GameValidator;
import com.epam.poker.service.validator.impl.GameWinnerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WriteStatisticResultGameImpl implements WriteStatisticResultGame {
    private static final Logger LOGGER = LogManager.getLogger();
    private static GameValidator gameValidator = GameValidator.getInstance();
    private static GamePlayerValidator gamePlayerValidator = GamePlayerValidator.getInstance();
    private static GameWinnerValidator gameWinnerValidator = GameWinnerValidator.getInstance();
    private static final String ERROR_MESSAGE = "Data is invalid!";
    private static WriteStatisticResultGame instance = new WriteStatisticResultGameImpl();

    public static WriteStatisticResultGame getInstance() {
        if (instance == null) {
            instance = new WriteStatisticResultGameImpl();
        }
        return instance;
    }

    @Override
    public long execute(StatisticResultGame statisticResultGame) throws ServiceException {
        if (!isValidData(statisticResultGame)) {
            LOGGER.warn(ERROR_MESSAGE);
            throw new ServiceException(ERROR_MESSAGE);
        }
        long gameId = -1;
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            GameDao gameDao = new GameDaoImpl();
            GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
            GameWinnerDao gameWinnerDao = new GameWinnerDaoImpl();
            try {
                transaction.initTransaction((AbstractDao) gameDao,
                        (AbstractDao) gamePlayerDao, (AbstractDao) gameWinnerDao);
                gameId = gameDao.add(statisticResultGame.getGame());
                for (GamePlayer gamePlayer : statisticResultGame.getGamePlayers()) {
                    gamePlayer.setGameId(gameId);
                    gamePlayerDao.add(gamePlayer);
                }
                for (GameWinner gameWinner : statisticResultGame.getGameWinners()) {
                    gameWinner.setGameId(gameId);
                    gameWinnerDao.add(gameWinner);
                }
                transaction.commit();
            } catch (DaoException e) {
                transaction.rollback();
                throw new ServiceException(e);
            } finally {
                transaction.endTransaction();
            }
        } catch (DaoException | ServiceException e) {
            throw new ServiceException(e);
        }
        return gameId;
    }

    private boolean isValidData(StatisticResultGame statisticResultGame) {
        if (!gameValidator.isValid(statisticResultGame.getGame())) {
            return false;
        }
        for (GamePlayer gamePlayer : statisticResultGame.getGamePlayers()) {
            if (!gamePlayerValidator.isValid(gamePlayer)) {
                return false;
            }
        }
        for (GameWinner gameWinner : statisticResultGame.getGameWinners()) {
            if (!gameWinnerValidator.isValid(gameWinner)) {
                return false;
            }
        }
        return true;
    }
}
