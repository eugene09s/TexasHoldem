package com.epam.poker.service.database.impl;

import com.epam.poker.dao.GameDao;
import com.epam.poker.dao.GamePlayerDao;
import com.epam.poker.dao.GameWinnerDao;
import com.epam.poker.dao.helper.DaoTransactionProvider;
import com.epam.poker.dao.impl.game.GameDaoImpl;
import com.epam.poker.dao.impl.game.GamePlayerDaoImpl;
import com.epam.poker.dao.impl.game.GameWinnerDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.Game;
import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.model.database.game.GameWinner;
import com.epam.poker.model.dto.StatisticResultGame;
import com.epam.poker.service.database.StatisticOfGameService;
import com.epam.poker.service.validator.impl.GamePlayerValidator;
import com.epam.poker.service.validator.impl.GameValidator;
import com.epam.poker.service.validator.impl.GameWinnerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StatisticOfGameServiceImpl implements StatisticOfGameService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final GameValidator gameValidator = GameValidator.getInstance();
    private static final GamePlayerValidator gamePlayerValidator = GamePlayerValidator.getInstance();
    private static final GameWinnerValidator gameWinnerValidator = GameWinnerValidator.getInstance();
    private static final String ERROR_MESSAGE = "Data is invalid!";
    private static StatisticOfGameService instance = new StatisticOfGameServiceImpl();

    public static StatisticOfGameService getInstance() {
        if (instance == null) {
            instance = new StatisticOfGameServiceImpl();
        }
        return instance;
    }

    @Override
    public long pushData(StatisticResultGame statisticResultGame) throws ServiceException {
        if (!isValidData(statisticResultGame)) {
            LOGGER.warn(ERROR_MESSAGE);
            throw new ServiceException(ERROR_MESSAGE);
        }
        long gameId = -1;
        GameDao gameDao = new GameDaoImpl();
        GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
        GameWinnerDao gameWinnerDao = new GameWinnerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(gameDao, gamePlayerDao, gameWinnerDao);
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
            throw new ServiceException(e);
        }
        return gameId;
    }

    @Override
    public List<StatisticResultGame> fetchDataByRange(int offset, int amount) throws ServiceException {
        List<StatisticResultGame> statisticResultGameList = new ArrayList<>();
        GameDao gameDao = new GameDaoImpl();
        GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
        GameWinnerDao gameWinnerDao = new GameWinnerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(gameDao, gamePlayerDao, gameWinnerDao);
            List<Game> gameList = gameDao.findGamesRange(offset, amount);
            for (Game game : gameList) {
                List<GamePlayer> gamePlayerList = gamePlayerDao.findGamePlayersByGameId(game.getGameId());
                List<GameWinner> gameWinnerList = gameWinnerDao.findGameWinnersByGameId(game.getGameId());
                StatisticResultGame statisticResultGame = StatisticResultGame.build()
                        .setGame(game)
                        .setGamePlayers(gamePlayerList)
                        .setGameWinners(gameWinnerList)
                        .createStatisticResultGame();
                statisticResultGameList.add(statisticResultGame);
            }
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return statisticResultGameList;
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
