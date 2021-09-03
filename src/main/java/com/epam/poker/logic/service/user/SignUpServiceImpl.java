package com.epam.poker.logic.service.user;

import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.User;

import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
    private final DaoSaveTransactionFactory daoSaveTransactionFactory;
    private final Validator<User> userValidator;


    public SignUpServiceImpl(DaoSaveTransactionFactory daoSaveTransactionFactory, Validator<User> userValidator) {
        this.daoSaveTransactionFactory = daoSaveTransactionFactory;
        this.userValidator = userValidator;
    }

    @Override
    public long signUp(User user) throws ServiceException {
        if (!userValidator.isValid(user)) {
            throw new ServiceException("Invalid user data.");
        }
        long userId = 0;
        try (DaoSaveTransaction daoSaveTransaction =
                     daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            userId = userDao.add(user);
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
