package com.epam.poker.model.service.database.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dao.AbstractDao;
import com.epam.poker.model.dao.helper.DaoSaveTransaction;
import com.epam.poker.model.dao.ProfilePlayerDao;
import com.epam.poker.model.dao.impl.user.ProfilePlayerDaoImpl;
import com.epam.poker.model.dao.UserDao;
import com.epam.poker.model.dao.impl.user.UserDaoImpl;
import com.epam.poker.model.entity.database.ProfilePlayer;
import com.epam.poker.model.entity.database.User;
import com.epam.poker.model.service.database.SignUpService;
import com.epam.poker.model.validator.Validator;
import com.epam.poker.model.validator.impl.ProfilePlayerValidator;
import com.epam.poker.model.validator.impl.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Validator<User> userValidator = UserValidator.getInstance();
    private static final Validator<ProfilePlayer> profilePlayerValidator = ProfilePlayerValidator.getInstance();
    private static SignUpService instance;

    private SignUpServiceImpl() {
    }

    public static SignUpService getInstance() {
        if (instance == null) {
            instance = new SignUpServiceImpl();
        }
        return instance;
    }

    @Override
    public long signUp(User user, ProfilePlayer profilePlayer)
            throws ServiceException, DaoException {
        if (!userValidator.isValid(user) || !profilePlayerValidator.isValid(profilePlayer)) {
            throw new ServiceException("Invalid user or profile player data.");
        }
        long userId = 0;
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        UserDao userDao = new UserDaoImpl();
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try {
            transaction.initTransaction((AbstractDao) userDao, (AbstractDao) profilePlayerDao);
            userId = userDao.add(user);
            profilePlayer.setUserId(userId);
            profilePlayerDao.add(profilePlayer);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
        return userId;
    }

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean isUserEmailExist(String email) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isPresent();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }
}
