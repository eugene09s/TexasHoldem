package com.epam.poker.service.database;

import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.ServiceException;

public interface AdminService {
    String banUserById(RequestContext requestContext) throws ServiceException;
    String unbanUserById(RequestContext requestContext) throws ServiceException;
    String changeBalanceUser(RequestContext requestContext) throws ServiceException;
}
