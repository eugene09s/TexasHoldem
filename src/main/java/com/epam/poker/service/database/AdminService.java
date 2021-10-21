package com.epam.poker.service.database;

import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.ServiceException;

/**
 * The interface Admin service execute only access command for admin role.
 */
public interface AdminService {
    /**
     * Ban user by id and get result operation.
     *
     * @param requestContext the request context
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    String banUserById(RequestContext requestContext) throws ServiceException;

    /**
     * Unban user by id and get result operation.
     *
     * @param requestContext the request context
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    String unbanUserById(RequestContext requestContext) throws ServiceException;

    /**
     * Change balance user and get result operation.
     *
     * @param requestContext the request context
     * @return the result operation
     * @throws ServiceException a wrapper for lower errors.
     */
    String changeBalanceUser(RequestContext requestContext) throws ServiceException;
}
