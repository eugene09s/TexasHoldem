package com.epam.poker._main;

import com.epam.poker.connection.ConnectionPool;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;
import com.epam.poker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TestDBMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String... args) {
        LOGGER.error("Test");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = User.builder()
                .setUserRole(UserRole.USER)
                .setUserStatus(UserStatus.ACTIVE)
                .setBalance(BigDecimal.TEN)
                .setEmail("Test4_@gmail.com")
                .setCreateTime(timestamp)
                .setLastName("Kolia")
                .setFirstName("Gardiam")
                .setLogin("Test4_Vadim1109")
                .setPassword("qrewrwrw")
                .createUser();

        //ConnectionPool connectionPool = new ConnectionPool();
        DaoSaveTransactionFactory daoSaveTransactionFactory = new DaoSaveTransactionFactory();
        DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create();
        UserDao userDao = daoSaveTransaction.createUserDao();
        long id = 0;
        try {
            id = userDao.add(user);
            user.setUserId(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(user);

        List<User> listUsers = null;
        try {
            listUsers = userDao.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(listUsers);
        try {
            System.out.println("Amount users: " + userDao.findUsersAmount());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
