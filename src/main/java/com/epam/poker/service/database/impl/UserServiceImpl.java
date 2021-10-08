package com.epam.poker.service.database.impl;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.impl.user.UserDaoImpl;
import com.epam.poker.model.database.User;
import com.epam.poker.model.database.type.UserStatus;
import com.epam.poker.service.database.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
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
    public List<User> findUsersRange(int offset, int amount) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.findUsersRange(offset, amount);
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
    public int findUsersAmount() throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.findUsersAmount();
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
    public User findUserByLoginPassword(String login, String password) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean blockById(long id) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.blockById(id);
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
    public boolean unblockById(long id) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.unblockById(id);
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
    public boolean addMoneyById(BigDecimal money, long id) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.addMoneyById(money, id);
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
    public boolean minusMoneyByLogin(BigDecimal money, String login) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.minusMoneyByLogin(money, login);
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
    public boolean addMoneyByLogin(BigDecimal money, String login) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.addMoneyByLogin(money, login);
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
    public boolean update(User item) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.update(item);
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
    public long add(User t) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.add(t);
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
    public boolean updatePassword(long userId, String password) throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.updatePassword(userId, password);
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
    public boolean isBlockedById(long id) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserExistByLoginPassword(String login, String password) throws ServiceException {
        try {
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
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            DaoSaveTransaction transaction = new DaoSaveTransaction();
            try {
                UserDao userDao = new UserDaoImpl();
                transaction.init((AbstractDao) userDao);
                return userDao.findAll();
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
    public User findUserById(long id) throws ServiceException {
        try {
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
                throw new ServiceException(e);
            } finally {
                transaction.end();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
