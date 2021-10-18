package com.epam.poker.service.database.impl;

import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoTransactionProvider;
import com.epam.poker.dao.impl.user.UserDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;
import com.epam.poker.model.database.type.UserStatus;
import com.epam.poker.service.database.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> findUsersRange(int offset, int amount) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.findUsersRange(offset, amount);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findUsersAmount() throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.findUsersAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByLoginPassword(String login, String password) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findUserByLoginPassword(login, password);
            if (user.isEmpty()) {
                throw new ServiceException("User with login=" + login + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isEmpty()) {
                throw new ServiceException("User with login=" + login + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findUserByEmail(email);
            if (user.isEmpty()) {
                throw new ServiceException("User with email=" + email + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean blockById(long id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.blockById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean unblockById(long id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.unblockById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addMoneyById(BigDecimal money, long id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.addMoneyById(money, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean minusMoneyByLogin(BigDecimal money, String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.minusMoneyByLogin(money, login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addMoneyByLogin(BigDecimal money, String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.addMoneyByLogin(money, login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(User item) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.update(item);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long add(User user) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.add(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBalanceByLogin(String login, BigDecimal money) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.updateBalanceByLogin(login, money);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBalanceById(long id, BigDecimal money) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.updateBalanceById(id, money);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isBlockedById(long id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findById(id);
            if (user.isEmpty()) {
                throw new ServiceException("User with id=" + id + " is not found.");
            }
            return user.get().getUserStatus() == UserStatus.BANNED;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserExistByLoginPassword(String login, String password) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findUserByLoginPassword(login, password);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserById(long id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findById(id);
            if (user.isEmpty()) {
                throw new ServiceException("User with id=" + id + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
