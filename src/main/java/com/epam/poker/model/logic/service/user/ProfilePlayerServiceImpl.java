package com.epam.poker.model.logic.service.user;

import com.epam.poker.model.dao.ProfilePlayerDao;
import com.epam.poker.model.dao.helper.DaoSaveTransaction;
import com.epam.poker.model.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.logic.validator.Validator;
import com.epam.poker.model.entity.ProfilePlayer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProfilePlayerServiceImpl implements ProfilePlayerService {
    private final DaoSaveTransactionFactory daoSaveTransactionFactory;
    private final Validator<ProfilePlayer> profilePlayerValidator;

    public ProfilePlayerServiceImpl(DaoSaveTransactionFactory daoSaveTransactionFactory,
                                    Validator<ProfilePlayer> profilePlayerValidator) {
        this.daoSaveTransactionFactory = daoSaveTransactionFactory;
        this.profilePlayerValidator = profilePlayerValidator;
    }

    @Override
    public int findProfilePlayerAmount() throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.findProfilePlayerAmount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProfilePlayer> findAll() throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProfilePlayer findProfilePlayerById(long id) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            Optional<ProfilePlayer> profilePlayer = profilePlayerDao.findById(id);
            if (!profilePlayer.isPresent()) {
                throw new ServiceException("Profile player id=" + id + " is not found");
            }
            return profilePlayer.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.updateProfilePlayerByUserId(userId, profilePlayer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updatePhotoByUserId(long userId, String photo) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.updatePhotoByUserId(userId, photo);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.updateBestPrizeByUserId(userId, bestPrize);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAwardByUserId(long userId, String award) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.updateAwardByUserId(userId, award);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.updateAboutYourselfByUserId(userId, aboutYourself);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            return profilePlayerDao.updateLostMoneyByUserId(userId, money);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
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
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            ProfilePlayerDao profilePlayerDao = daoSaveTransaction.createProfilePlayerDao();
            profilePlayerDao.add(profilePlayer);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
