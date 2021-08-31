package com.epam.poker.connection;

import com.epam.poker.exception.ConnectionPoolException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    private static final Properties properties = new Properties();
    private static final String DB_PROPERTIES_LOCATION = "prop/database.properties";
    private static final String URL_PROPERTY_KEY = "db.url";
    private static final String DB_DRIVER_PROPERTY_KEY = "db.driver";
    private static final String DB_URL;

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

    public ConnectionCreator() {}

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }
}
