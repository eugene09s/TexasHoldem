package com.epam.poker.model.service.user;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dao.AbstractDao;
import com.epam.poker.model.dao.helper.DaoSaveTransaction;
import com.epam.poker.model.dao.UserDao;
import com.epam.poker.model.dao.impl.user.UserDaoImpl;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.entity.type.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> findUsersRange(int offset, int amount)
            throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            List<User> users = userDao.findUsersRange(offset, amount);
            return users;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int findUsersAmount() throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            int amount = userDao.findUsersAmount();
            return amount;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public User findUserByLoginPassword(String login, String password) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findUserByLoginPassword(login, password);
            if (!user.isPresent()) {
                throw new ServiceException("User with login=" + login + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findUserByLogin(login);
            if (!user.isPresent()) {
                throw new ServiceException("User with login=" + login + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public User findUserByEmail(String email) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findUserByEmail(email);
            if (!user.isPresent()) {
                throw new ServiceException("User with email=" + email + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean blockById(long id) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            boolean answer = userDao.blockById(id);
            return answer;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean unblockById(long id) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            boolean answer = userDao.unblockById(id);
            return answer;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean addMoneyById(BigDecimal money, long id) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            boolean answer = userDao.addMoneyById(money, id);
            return answer;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean update(User item) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            boolean answer = userDao.update(item);
            return answer;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public long add(User t) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            long id = userDao.add(t);
            return id;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean updatePassword(long userId, String password) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            boolean answer = userDao.updatePassword(userId, password);
            return answer;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean isBlockedById(long id) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findById(id);
            if (!user.isPresent()) {
                throw new ServiceException("User with id=" + id + " is not found.");
            }
            return user.get().getUserStatus() == UserStatus.BANNED;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean isUserExistByLoginPassword(String login, String password) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findUserByLoginPassword(login, password);
            return user.isPresent();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<User> findAll() throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            List<User> users = userDao.findAll();
            return users;
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public User findUserById(long id) throws ServiceException, DaoException {
        DaoSaveTransaction transaction = new DaoSaveTransaction();
        try {
            UserDao userDao = new UserDaoImpl();
            transaction.init((AbstractDao) userDao);
            Optional<User> user = userDao.findById(id);
            if (!user.isPresent()) {
                throw new ServiceException("User with id=" + id + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            LOGGER.error("Transaction error: " + e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }
}
