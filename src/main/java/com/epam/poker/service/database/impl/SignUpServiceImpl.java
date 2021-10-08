package com.epam.poker.service.database.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.dao.impl.user.ProfilePlayerDaoImpl;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.impl.user.UserDaoImpl;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.service.database.SignUpService;
import com.epam.poker.service.validator.Validator;
import com.epam.poker.service.validator.impl.ProfilePlayerValidator;
import com.epam.poker.service.validator.impl.UserValidator;
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
    public long signUp(User user, ProfilePlayer profilePlayer) throws ServiceException {
        if (!userValidator.isValid(user) || !profilePlayerValidator.isValid(profilePlayer)) {
            throw new ServiceException("Invalid user or profile player data.");
        }
        long userId = 0;
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userId;
    }

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserEmailExist(String email) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
