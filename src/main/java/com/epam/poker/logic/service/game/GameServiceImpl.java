package com.epam.poker.logic.service.game;

import com.epam.poker.dao.GameDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.game.Game;

import java.util.List;
import java.util.Optional;

public class GameServiceImpl implements GameService {
    private final DaoSaveTransactionFactory daoSaveTransactionFactory;
    private final Validator<Game> gameValidator;

    public GameServiceImpl(DaoSaveTransactionFactory daoSaveTransactionFactory,
                           Validator<Game> gameValidator) {
        this.daoSaveTransactionFactory = daoSaveTransactionFactory;
        this.gameValidator = gameValidator;
    }

    @Override
    public long add(Game game) throws ServiceException {
        if (!gameValidator.isValid(game)) {
            throw new ServiceException("Invalid game data.");
        }
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            GameDao gameDao = daoSaveTransaction.createGameDao();
            return gameDao.add(game);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findGamesAmout() throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            GameDao gameDao = daoSaveTransaction.createGameDao();
            return gameDao.findGameAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Game> findGamesRange(int offset, int amount) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            GameDao gameDao = daoSaveTransaction.createGameDao();
            return gameDao.findGamesRange(offset, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Game findGameById(long id) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            GameDao gameDao = daoSaveTransaction.createGameDao();
            Optional<Game> gameOptional = gameDao.findById(id);
            if (!gameOptional.isPresent()) {
                throw new ServiceException("Game id=" + id + " is not found");
            }
            return gameOptional.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
