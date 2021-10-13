package com.epam.poker.service.database.impl;

import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.impl.user.ProfilePlayerDaoImpl;
import com.epam.poker.dao.impl.user.UserDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;
import com.epam.poker.service.database.AccountInfoChangeService;
import com.epam.poker.service.database.SignUpService;
import com.epam.poker.service.validator.impl.UserValidator;
import com.epam.poker.util.LineHasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountInfoChangeServiceImpl implements AccountInfoChangeService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static SignUpService signUpService = SignUpServiceImpl.getInstance();
    private static AccountInfoChangeService instance;

    public static AccountInfoChangeService getInstance() {
        if (instance == null) {
            instance = new AccountInfoChangeServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean updatePassword(long userId, String currentPassword, String newPassword) throws ServiceException {
        UserValidator generalInfoValidator = UserValidator.getInstance();
        if (!generalInfoValidator.isValidPassword(currentPassword)
                || !generalInfoValidator.isValidPassword(newPassword)) {
            LOGGER.info("Update password invalid: ");
            return false;
        }
        LineHasher lineHasher = new LineHasher();
        String hashCurrentPassword = lineHasher.hashingLine(currentPassword);
        String hashNewPassword = lineHasher.hashingLine(newPassword);
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init(userDao);
                String trueHashPassword = userDao.findById(userId).get().getPassword();
                if (hashCurrentPassword.equals(trueHashPassword)) {
                    userDao.updatePasswordByUserId(userId, hashNewPassword);
                    return true;
                }
            } finally {
                transaction.end();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public boolean updateGeneralInfo(long userId, User user, String bio) throws ServiceException {
        UserValidator modifiableDataOfUserValidator = UserValidator.getInstance();
        if (!modifiableDataOfUserValidator.isValidGeneralInfo(user)
                || !modifiableDataOfUserValidator.isValidBio(bio)) {
            LOGGER.info("Update password invalid: ");
            return false;
        }
        LOGGER.info("2");
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
                transaction.initTransaction(userDao, profilePlayerDao);
                userDao.updateGeneralInfoByUserId(userId, user);
                profilePlayerDao.updateAboutYourselfByUserId(userId, bio);
            } catch (DaoException e) {
                transaction.rollback();
                throw new ServiceException(e);
            }finally {
                LOGGER.info("4");
                transaction.endTransaction();
            }
        } catch (DaoException | ServiceException e) {
            throw new ServiceException(e);
        }
        LOGGER.info("3");
        return true;
    }
}
