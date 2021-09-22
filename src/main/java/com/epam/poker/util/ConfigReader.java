package com.epam.poker.util;

import com.epam.poker.exception.ConnectionPoolException;
import com.epam.poker.model.pool.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final String CONFIG_APP_PROPERTIES_LOCATION = "prop/application.properties";
    private static String SECRET_KEY_PROPERTIES = "jwt.secretKey";
    private static String secretKey;

    static {
        try (InputStream inputStream =
                     ConnectionPool.class.getClassLoader().getResourceAsStream(CONFIG_APP_PROPERTIES_LOCATION)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            secretKey = (String) properties.get(SECRET_KEY_PROPERTIES);
        } catch (IOException e) {
            throw new ConnectionPoolException(e);
        }
    }

    private ConfigReader() {}

    public static String getSecretKey() {
        return secretKey;
    }
}
