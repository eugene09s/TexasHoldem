package com.epam.poker.service.database.impl;

import com.epam.poker.dao.GamePlayerDao;
import com.epam.poker.dao.helper.DaoTransactionProvider;
import com.epam.poker.dao.impl.game.GamePlayerDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.service.database.GamePlayerService;
import com.epam.poker.service.validator.Validator;
import com.epam.poker.service.validator.impl.GamePlayerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GamePlayerServiceImpl implements GamePlayerService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Validator<GamePlayer> gamePlayerValidator = GamePlayerValidator.getInstance();
    private static GamePlayerService instance;

    public static GamePlayerService getInstance() {
        if (instance == null) {
            instance = new GamePlayerServiceImpl();
        }
        return instance;
    }

    @Override
    public long add(GamePlayer gamePlayer) throws ServiceException {
        if (!gamePlayerValidator.isValid(gamePlayer)) {
            throw new ServiceException("Invalid game player data.");
        }
        GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(gamePlayerDao);
            return gamePlayerDao.add(gamePlayer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GamePlayer> findGamePlayersAll() throws ServiceException {
        GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(gamePlayerDao);
            return gamePlayerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findGamePlayersAmount() throws ServiceException {
        GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(gamePlayerDao);
            return gamePlayerDao.findGamePLayerAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GamePlayer> findGamePlayersByGameId(long gameId) throws ServiceException {
        GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(gamePlayerDao);
            return gamePlayerDao.findGamePlayersByGameId(gameId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GamePlayer> findGamePlayersByUserId(long userId) throws ServiceException {
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            GamePlayerDao gamePlayerDao = new GamePlayerDaoImpl();
            transaction.initTransaction(gamePlayerDao);
            return gamePlayerDao.findGamePlayersByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
