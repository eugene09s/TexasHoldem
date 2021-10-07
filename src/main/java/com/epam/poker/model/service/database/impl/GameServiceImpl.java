package com.epam.poker.model.service.database.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dao.AbstractDao;
import com.epam.poker.model.dao.helper.DaoSaveTransaction;
import com.epam.poker.model.dao.GameDao;
import com.epam.poker.model.dao.impl.game.GameDaoImpl;
import com.epam.poker.model.entity.database.game.Game;
import com.epam.poker.model.service.database.GameService;
import com.epam.poker.model.validator.Validator;
import com.epam.poker.model.validator.impl.GameValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class GameServiceImpl implements GameService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Validator<Game> gameValidator = GameValidator.getInstance();
    private static GameService instance;

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameServiceImpl();
        }
        return instance;
    }

    @Override
    public long add(Game game) throws ServiceException, DaoException {
        if (!gameValidator.isValid(game)) {
            throw new ServiceException("Invalid game data.");
        }
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            GameDao gameDao = new GameDaoImpl();
            transaction.init((AbstractDao) gameDao);
            return gameDao.add(game);
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int findGamesAmout() throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            GameDao gameDao = new GameDaoImpl();
            transaction.init((AbstractDao) gameDao);
            return gameDao.findGameAmount();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Game> findGamesRange(int offset, int amount) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            GameDao gameDao = new GameDaoImpl();
            transaction.init((AbstractDao) gameDao);
            return gameDao.findGamesRange(offset, amount);
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Game findGameById(long id) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            GameDao gameDao = new GameDaoImpl();
            transaction.init((AbstractDao) gameDao);
            Optional<Game> gameOptional = gameDao.findById(id);
            if (!gameOptional.isPresent()) {
                throw new ServiceException("Game id=" + id + " is not found");
            }
            return gameOptional.get();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }
}
