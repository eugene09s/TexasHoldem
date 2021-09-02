package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.entity.ProfilePlayer;
import com.epam.poker.model.entity.User;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

/*
 *   Extending DAO interface for processing ProfilePlayer
 */
public interface ProfilePlayerDao extends Dao<ProfilePlayer> {

        /**
         *   Gets list profile players in range described as offset and amount of profile player.
         *
         *   @param offset an amount of profile players to get.
         *
         *   @return a received list of profile players.
         *
         *   @throws DaoException if database errors occurs.
         */
        List<User> findProfilePlayerRange(int offset, int amount) throws DaoException;

        /**
         * Gets profile players amount in database.
         *
         * @return  an amount of all profile players.
         *
         * @throws  DaoException  if database errors occurs.
         */
        int findProfilePlayerAmount() throws DaoException;
//todo documentation code
        boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws DaoException;
        boolean updatePhotoByUserId(long userId, Blob photo) throws DaoException;
        boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws DaoException;
        boolean updateAwardByUserId(long userId, String award) throws DaoException;
        boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws DaoException;
        boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws DaoException;
        boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws DaoException;
        long add(ProfilePlayer profilePlayer, long userId) throws DaoException;
}
