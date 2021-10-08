package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;

/**
 * Interface with description of operations in sign up command.
 */
public interface SignUpService {
    /**
     * Authorizes user by login and password.
     *
     * @param user  a ...of user.
     *
     * @return a boolean result confirm of register
     *
     * @throws ServiceException if login and password are not passed validation
     *                          and also it's a wrapper for lower errors.
     */
    long signUp(User user, ProfilePlayer profilePlayer) throws ServiceException;

    /**
     * Finds out if user is exist by login.
     * @param login a login of user.
     *
     * @return a boolean result of finding.
     *
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean isUserLoginExist(String login) throws ServiceException;

    /**
     * Finds out if user is exist by login.
     * @param email a login of user.
     *
     * @return a boolean result of finding.
     *
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean isUserEmailExist(String email) throws ServiceException;
}
