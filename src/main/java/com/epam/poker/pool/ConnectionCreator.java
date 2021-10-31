package com.epam.poker.pool;

import com.epam.poker.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class ConnectionCreator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DB_PROPERTIES_LOCATION = "prop/database.properties";
    private static final String URL_PROPERTY_KEY = "db.url";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String MESSAGE_ERROR = "Don't upload config for Connection creator: ";
    private static final String DB_URL;

    static {
        Map<String, String> environmentVariables = System.getenv();
        String dbName = environmentVariables.get("DB_NAME");
        if (dbName == null) {
            try (InputStream inputStream =
                         ConnectionCreator.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_LOCATION);) {
                properties.load(inputStream);
            } catch (IOException e) {
                throw new ConnectionPoolException(e);
            }
            DB_URL = (String) properties.get(URL_PROPERTY_KEY);
            LOGGER.info("Read default config variables: " + DB_URL);
        } else {
            DB_URL = "jdbc:mysql://" + environmentVariables.get("DB_HOST") + ":" +
                    environmentVariables.get("DB_PORT") + "/" + dbName;
            properties.put("user", environmentVariables.get("DB_USER"));
            properties.put("password", environmentVariables.get("DB_PASSWORD"));
            properties.put("encoding", "utf8mb4");
            LOGGER.info("Read environment variables: " + DB_URL);
        }
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
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
