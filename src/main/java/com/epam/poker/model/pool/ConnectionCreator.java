package com.epam.poker.model.pool;

import com.epam.poker.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DB_PROPERTIES_LOCATION = "prop/database.properties";
    private static final String URL_PROPERTY_KEY = "db.url";
    private static final String DB_DRIVER_PROPERTY_KEY = "db.driver";
    private static final String DB_URL;
    public static final String MESSAGE_ERROR = "Don't upload config for Connection creator: ";

    static {
        try (InputStream inputStream =
                     ConnectionCreator.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_LOCATION);) {
            properties.load(inputStream);
            String driverName = (String) properties.get(DB_DRIVER_PROPERTY_KEY);
            Class.forName(driverName);
        } catch (IOException | ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
        DB_URL = (String) properties.get(URL_PROPERTY_KEY);
    }

    protected ConnectionCreator() {}

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            LOGGER.fatal(MESSAGE_ERROR + e);
            throw new ConnectionPoolException(e);
        }
    }
}
