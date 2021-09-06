package com.epam.poker.logic.service.user;

import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.ProfilePlayer;
import com.epam.poker.model.entity.User;

import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
    private final DaoSaveTransactionFactory daoSaveTransactionFactory;
    private final Validator<User> userValidator;
    private final Validator<ProfilePlayer> profilePlayerValidator;


    public SignUpServiceImpl(DaoSaveTransactionFactory daoSaveTransactionFactory,
                             Validator<User> userValidator, Validator<ProfilePlayer> profilePlayerValidator) {
        this.daoSaveTransactionFactory = daoSaveTransactionFactory;
        this.userValidator = userValidator;
        this.profilePlayerValidator = profilePlayerValidator;
    }

    @Override
    public long signUp(User user, ProfilePlayer profilePlayer) throws ServiceException {
        if (!userValidator.isValid(user) || profilePlayerValidator.isValid(profilePlayer)) {
            throw new ServiceException("Invalid user or profile player data.");
        }
        long userId = 0;
        try (DaoSaveTransaction daoSaveTransaction =
                     daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            UserDao userDao = daoSaveTransaction.createUserDao();
            userId = userDao.add(user);
            profilePlayer.setUserId(userId);
            profilePlayerDao.add(profilePlayer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userId;
    }

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction =
                     daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserEmailExist(String email) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction =
                     daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
