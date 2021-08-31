package com.epam.poker._main;

import com.epam.poker.connection.ConnectionCreator;
import com.epam.poker.connection.ConnectionPool;
import com.epam.poker.domain.dao.UserDao;
import com.epam.poker.domain.dao.helper.DaoSaveTransaction;
import com.epam.poker.domain.model.entity.User;
import com.epam.poker.domain.model.enumeration.UserRole;
import com.epam.poker.domain.model.enumeration.UserStatus;
import com.epam.poker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class TestDBMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String... args) {
//        ConnectionCreator connectionFactory = new ConnectionCreator();
//
        User user = User.builder()
                .setUserId(432432)
                .setUserRole(UserRole.USER)
                .setUserStatus(UserStatus.ACTIVE)
                .setAboutYourselt("")
                .setBalance(BigDecimal.TEN)
                .setCreateTime(new Timestamp(10))
                .setEmail("")
                .setLastName("")
                .setFirstName("")
                .setLogin("")
                .setPassword("")
                .createUser();
//
        InputStream inputStream =
                ConnectionPool.class.getClassLoader().getResourceAsStream("prop/database.properties");
        System.out.println(inputStream);
        try (Connection connection = ConnectionCreator.createConnection();
             Statement statement = connection.createStatement();) {
            LOGGER.info("OK");
            System.out.println("OK");
        } catch (SQLException e) {
            System.out.println(e);
            LOGGER.error(e);
        }
        ConnectionPool connectionPool = new ConnectionPool();
        DaoSaveTransaction daoSaveTransaction = new DaoSaveTransaction(connectionPool);
        UserDao userDao = daoSaveTransaction.createUserDao();
        List<User> listUsers = null;
        try {
            listUsers = userDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        System.out.println(listUsers);
    }

}
