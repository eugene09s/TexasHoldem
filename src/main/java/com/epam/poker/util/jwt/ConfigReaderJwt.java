package com.epam.poker.util.jwt;

import com.epam.poker.exception.ConnectionPoolException;
import com.epam.poker.model.pool.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReaderJwt {
    private static final String CONFIG_APP_PROPERTIES_LOCATION = "prop/application.properties";
    private static String SECRET_KEY_PROPERTIES = "jwt.secretKey";
    private static String ACCESS_TOKEN_LIFE_TIME_PROPERTIES = "jwt.accessToken.lifeTime";
    private static String secretKey;
    private static long accessTokenLifeTime;

    static {
        try (InputStream inputStream =
                     ConnectionPool.class.getClassLoader()
                             .getResourceAsStream(CONFIG_APP_PROPERTIES_LOCATION)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            secretKey = (String) properties.get(SECRET_KEY_PROPERTIES);
            accessTokenLifeTime = Long.parseLong((String) properties.get(ACCESS_TOKEN_LIFE_TIME_PROPERTIES));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error read application properties!");
        }
    }

    private ConfigReaderJwt() {}

    public static String getSecretKey() {
        return secretKey;
    }

    public static long getAccessTokenLifeTime() {
        return accessTokenLifeTime;
    }
}
