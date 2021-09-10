package com.epam.poker.model.service.user;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.entity.ProfilePlayer;

import java.math.BigDecimal;
import java.util.List;

public interface ProfilePlayerService {
    /**
     * Gets users amount in database.
     *
     * @return  an amount of all users.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    int findProfilePlayerAmount() throws ServiceException, DaoException;

    /**
     * Finds user in database by login and password and returns container of account
     * or empty container if not found.
     *
     *
     * @return  an optional container of account.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    List<ProfilePlayer> findAll() throws ServiceException, DaoException;

    /**
     * Finds user in database by login and returns container of account
     * or empty container if not found.
     *
     * @param  id  a login(username) of account.
     *
     * @return  an optional container of account.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    ProfilePlayer findProfilePlayerById(long id) throws ServiceException, DaoException;
    boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws ServiceException, DaoException;
    boolean updatePhotoByUserId(long userId, String photo) throws ServiceException, DaoException;
    boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws ServiceException, DaoException;
    boolean updateAwardByUserId(long userId, String award) throws ServiceException, DaoException;
    boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws ServiceException, DaoException;
    boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws ServiceException, DaoException;
    boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws ServiceException, DaoException;
    void add(ProfilePlayer profilePlayer) throws ServiceException, DaoException;
}
