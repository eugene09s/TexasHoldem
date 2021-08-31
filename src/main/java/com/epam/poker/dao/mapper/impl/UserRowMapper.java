package com.epam.poker.dao.mapper.impl;

import com.epam.poker.dao.mapper.RowMapper;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .setUserId(resultSet.getLong(USER_ID))
                .setLogin(resultSet.getString(USER_LOGIN))
                .setPassword(resultSet.getString(USER_PASSWORD))
                .setFirstName(resultSet.getString(USER_FIRST_NAME))
                .setLastName(resultSet.getString(USER_LAST_NAME))
                .setEmail(resultSet.getString(USER_EMAIL))
                .setBalance(resultSet.getBigDecimal(USER_BALANCE))
                .setPhoto(resultSet.getBlob(USER_PHOTO))
                .setCreateTime(resultSet.getTimestamp(USER_CREATE_TIME))
                .setPhoneNumber(Long.parseLong(resultSet.getString(USER_PHONE_NUMBER)))
                .setAboutYourselt(resultSet.getString(USER_ABOUT_YOURSELF))
                .setUserRole(UserRole.valueOf(resultSet.getString(ROLE_ROLE)))
                .setUserStatus(UserStatus.valueOf(resultSet.getString(STATUS_STATUS)))
                .createUser();
        return user;
    }
}
