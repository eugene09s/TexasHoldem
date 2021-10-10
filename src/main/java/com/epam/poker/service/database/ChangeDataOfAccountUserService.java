package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;

public interface ChangeDataOfAccountUserService {
    boolean updatePassword(long userId, String currentPassword, String newPassword) throws ServiceException;
    boolean updateGeneralInfo(long userId, User user, String bio) throws ServiceException;
}
