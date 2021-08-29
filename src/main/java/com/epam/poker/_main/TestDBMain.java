package com.epam.poker._main;

import com.epam.poker.connection.ConnectionCreator;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String... args) {
        ConnectionCreator connectionFactory = new ConnectionCreator();

        User user = User.builder()
                .setUserId(432432)
                .setUserRole(UserRole.USER)
                .setUserStatus(UserStatus.ACTIVE)
                .setAboutYourselt("")
                .setBalance(BigDecimal.TEN)
                .setCreateTime("")
                .setEmail("")
                .setLastName("")
                .setFirstName("")
                .setLogin("")
                .setPassword("")
                .setPhoto("")
                .createUser();

        try (Connection connection = ConnectionCreator.createConnection();
             Statement statement = connection.createStatement();) {
            LOGGER.info("OK");
            System.out.println("OK");
        } catch (SQLException e) {
            System.out.println(e);
            LOGGER.error(e);
        }
        System.out.println(user);
    }
}
