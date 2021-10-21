package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;

/**
 * The interface Account info change service, user operations.
 */
public interface AccountInfoChangeService {
    /**
     * Update password by user id and get result operation.
     *
     * @param userId          the user id
     * @param currentPassword the current password
     * @param newPassword     the new password
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updatePassword(long userId, String currentPassword, String newPassword) throws ServiceException;

    /**
     * Update general info by user id and get result operation.
     *
     * @param userId the user id
     * @param user   the user
     * @param bio    the about yourself of user
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateGeneralInfo(long userId, User user, String bio) throws ServiceException;
}
