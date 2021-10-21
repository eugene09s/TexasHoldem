package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface with description of operations with the User.
 */
public interface ProfilePlayerService {
    /**
     * Find profile player amount in database.
     *
     * @return the int
     * @throws ServiceException a wrapper for lower errors.
     */
    int findProfilePlayerAmount() throws ServiceException;

    /**
     * Fetch all profile players from database.
     *
     * @return the list
     * @throws ServiceException a wrapper for lower errors.
     */
    List<ProfilePlayer> findAll() throws ServiceException;

    /**
     * Find profile player by id profile player from database.
     *
     * @param id the id profile player
     * @return the profile player
     * @throws ServiceException a wrapper for lower errors.
     */
    ProfilePlayer findProfilePlayerById(long id) throws ServiceException;

    /**
     * Update profile player in database by user id.
     *
     * @param userId        the user id
     * @param profilePlayer the profile player
     * @return the boolean
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws ServiceException;

    /**
     * Update photo in database by user id.
     *
     * @param userId the user id
     * @param photo  the photo
     * @return result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updatePhotoByUserId(long userId, String photo) throws ServiceException;

    /**
     * Update award in database by user id.
     *
     * @param userId the user id
     * @param award  the award of User
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateAwardByUserId(long userId, String award) throws ServiceException;

    /**
     * Update about yourself in database by user id.
     *
     * @param userId        the user id
     * @param aboutYourself the about yourself
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws ServiceException;

    /**
     * Update best prize in database by user id.
     *
     * @param userId    the user id
     * @param bestPrize the best prize
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws ServiceException;

    /**
     * Update win money in database by user id.
     *
     * @param userId the user id
     * @param money  the money
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws ServiceException;

    /**
     * Update lost money in database by user id.
     *
     * @param userId the user id
     * @param money  the money
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws ServiceException;

    /**
     * Update lost money in database by login user.
     *
     * @param login the login of user
     * @param money the money
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateLostMoneyByLogin(String login, BigDecimal money) throws ServiceException;

    /**
     * Update win money in database by login user.
     *
     * @param login the login of user
     * @param money the money
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateWinMoneyByLogin(String login, BigDecimal money) throws ServiceException;

    /**
     * Update best prize in database by login user.
     *
     * @param login     the login
     * @param bestPrize the best prize
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateBestPrizeByLogin(String login, BigDecimal bestPrize) throws ServiceException;

    /**
     * Find profile player in database of range list.
     *
     * @param offset the offset in database
     * @param size   the size of profile players
     * @return the list of profile players
     * @throws ServiceException a wrapper for lower errors.
     */
    List<ProfilePlayer> findProfilePlayerOfRange(int offset, int size) throws ServiceException;

    /**
     * Add new ProfilePlayer by user id in database.
     *
     * @param profilePlayer the profile player
     * @throws ServiceException a wrapper for lower errors.
     */
    void add(ProfilePlayer profilePlayer) throws ServiceException;
}
