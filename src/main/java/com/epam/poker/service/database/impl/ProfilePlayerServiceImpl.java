package com.epam.poker.service.database.impl;

import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.impl.user.UserDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.dao.helper.DaoTransactionProvider;
import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.dao.impl.user.ProfilePlayerDaoImpl;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.validator.Validator;
import com.epam.poker.service.validator.impl.ProfilePlayerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProfilePlayerServiceImpl implements ProfilePlayerService {
    private static final Validator<ProfilePlayer> profilePlayerValidator = ProfilePlayerValidator.getInstance();
    private static ProfilePlayerService instance;

    public static ProfilePlayerService getInstance() {
        if (instance == null) {
            instance = new ProfilePlayerServiceImpl();
        }
        return instance;
    }

    @Override
    public int findProfilePlayerAmount() throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.findProfilePlayerAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProfilePlayer> findAll() throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProfilePlayer findProfilePlayerById(long id) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            Optional<ProfilePlayer> profilePlayer = profilePlayerDao.findById(id);
            if (profilePlayer.isEmpty()) {
                throw new ServiceException("Profile player id=" + id + " is not found");
            }
            return profilePlayer.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updateProfilePlayerByUserId(userId, profilePlayer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updatePhotoByUserId(long userId, String photo) throws ServiceException {
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updatePhotoByUserId(userId, photo);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updateBestPrizeByUserId(userId, bestPrize);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateLostMoneyByLogin(String login, BigDecimal money) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao, profilePlayerDao);
            Optional<User> userOptional = userDao.findUserByLogin(login);
            if (userOptional.isEmpty()) {
                throw new ServiceException("User login=" + login + " is not found");
            }
            long userId = userOptional.get().getUserId();
            boolean isUpdated = profilePlayerDao.updateLostMoneyByUserId(userId, money);
            transaction.commit();
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateWinMoneyByLogin(String login, BigDecimal money) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao, profilePlayerDao);
            Optional<User> userOptional = userDao.findUserByLogin(login);
            if (userOptional.isEmpty()) {
                throw new ServiceException("User login=" + login + " is not found");
            }
            long userId = userOptional.get().getUserId();
            boolean isUpdated = profilePlayerDao.updateWinMoneyByUserId(userId, money);
            transaction.commit();
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBestPrizeByLogin(String login, BigDecimal money) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao, profilePlayerDao);
            Optional<User> userOptional = userDao.findUserByLogin(login);
            if (userOptional.isEmpty()) {
                throw new ServiceException("User login=" + login + " is not found");
            }
            long userId = userOptional.get().getUserId();
            boolean isUpdated = profilePlayerDao.updateBestPrizeByUserId(userId, money);
            transaction.commit();
            return isUpdated;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProfilePlayer> findProfilePlayerOfRange(int offset, int size) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.findProfilePlayerOfRange(offset, size);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAwardByUserId(long userId, String award) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updateAwardByUserId(userId, award);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updateAboutYourselfByUserId(userId, aboutYourself);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updateLostMoneyByUserId(userId, money);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws ServiceException {
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            return profilePlayerDao.updateWinMoneyByUserId(userId, money);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void add(ProfilePlayer profilePlayer) throws ServiceException {
        if (!profilePlayerValidator.isValid(profilePlayer)) {
            throw new ServiceException("Invalid profile player data.");
        }
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(profilePlayerDao);
            profilePlayerDao.add(profilePlayer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}