package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.ProfilePlayer;

import java.math.BigDecimal;
import java.util.List;

/*
 *   Extending DAO interface for processing ProfilePlayer
 */
public interface ProfilePlayerDao extends Dao<ProfilePlayer> {
        /**
         * Gets profile players amount in database.
         *
         * @return an amount of all profile players.
         * @throws DaoException if database errors occurs.
         */
        int findProfilePlayerAmount() throws DaoException;

        /**
         * Update profile player by user id boolean.
         *
         * @param userId        the user id
         * @param profilePlayer the profile player
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws DaoException;

        /**
         * Update photo by user id boolean.
         *
         * @param userId the user id
         * @param photo  the photo
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updatePhotoByUserId(long userId, String photo) throws DaoException;

        /**
         * Update best prize by user id boolean.
         *
         * @param userId    the user id
         * @param bestPrize the best prize
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws DaoException;

        /**
         * Update award by user id boolean.
         *
         * @param userId the user id
         * @param award  the award
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updateAwardByUserId(long userId, String award) throws DaoException;

        /**
         * Update about yourself by user id boolean.
         *
         * @param userId        the user id
         * @param aboutYourself the about yourself
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws DaoException;

        /**
         * Update lost money by user id boolean.
         *
         * @param userId the user id
         * @param money  the money
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws DaoException;

        /**
         * Update win money by user id boolean.
         *
         * @param userId the user id
         * @param money  the money
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws DaoException;

        /**
         * Find profile player of range list.
         *
         * @param offset the offset
         * @param amount the amount
         * @return the list
         * @throws DaoException the dao exception
         */
        List<ProfilePlayer> findProfilePlayerOfRange(int offset, int amount) throws DaoException;

        /**
         * Add boolean.
         *
         * @param profilePlayer field userId should be
         * @return the boolean
         * @throws DaoException the dao exception
         */
        boolean add(ProfilePlayer profilePlayer) throws DaoException;
}
