package com.epam.poker.util.jwt;

import com.epam.poker.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public final class ConfigReaderJwt {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONFIG_APP_PROPERTIES_LOCATION = "prop/application.properties";
    private static final String SECRET_KEY_PROPERTIES = "jwt.secretKey";
    private static final String ACCESS_TOKEN_LIFE_TIME_PROPERTIES = "jwt.accessToken.lifeTime";
    private static final String SECRET_KEY;
    private static final long ACCESS_TOKEN_LIFE_TIME;

    static {
        Map<String, String> environmentVariables = System.getenv();
        String lineSecretKey = environmentVariables.get("JWT_SECRET");
        if (lineSecretKey == null) {
            try (InputStream inputStream =
                         ConnectionPool.class.getClassLoader()
                                 .getResourceAsStream(CONFIG_APP_PROPERTIES_LOCATION)) {
                Properties properties = new Properties();
                properties.load(inputStream);
                SECRET_KEY = (String) properties.get(SECRET_KEY_PROPERTIES);
                ACCESS_TOKEN_LIFE_TIME = Long.parseLong((String) properties.get(ACCESS_TOKEN_LIFE_TIME_PROPERTIES));
            } catch (IOException e) {
                throw new IllegalArgumentException("Error read application properties!");
            }
            LOGGER.info("Read default token life time: " + ACCESS_TOKEN_LIFE_TIME);
        } else {
            SECRET_KEY = lineSecretKey;
            ACCESS_TOKEN_LIFE_TIME = Integer.parseInt(environmentVariables.get("JWT_LIFE_TIME"));
            LOGGER.info("Read environment variable token life time: " + ACCESS_TOKEN_LIFE_TIME);
        }
    }

    private ConfigReaderJwt() {}

    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public static long getAccessTokenLifeTime() {
        return ACCESS_TOKEN_LIFE_TIME;
    }
}
