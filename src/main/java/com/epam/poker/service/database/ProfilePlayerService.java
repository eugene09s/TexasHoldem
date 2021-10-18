package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;

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
    int findProfilePlayerAmount() throws ServiceException;

    /**
     * Finds user in database by login and password and returns container of account
     * or empty container if not found.
     *
     *
     * @return  an optional container of account.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    List<ProfilePlayer> findAll() throws ServiceException;

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
    ProfilePlayer findProfilePlayerById(long id) throws ServiceException;
    boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws ServiceException;
    boolean updatePhotoByUserId(long userId, String photo) throws ServiceException;
    boolean updateAwardByUserId(long userId, String award) throws ServiceException;
    boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws ServiceException;
    boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws ServiceException;
    boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws ServiceException;
    boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws ServiceException;
    boolean updateLostMoneyByLogin(String login, BigDecimal money) throws ServiceException;
    boolean updateWinMoneyByLogin(String login, BigDecimal money) throws ServiceException;
    boolean updateBestPrizeByLogin(String login, BigDecimal bestPrize) throws ServiceException;
    List<ProfilePlayer> findProfilePlayerOfRange(int offset, int size) throws ServiceException;
    void add(ProfilePlayer profilePlayer) throws ServiceException;
}
