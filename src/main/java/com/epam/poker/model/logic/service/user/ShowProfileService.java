package com.epam.poker.model.logic.service.user;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.entity.ProfilePlayer;

import java.util.List;

public interface ShowProfileService {
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
}
