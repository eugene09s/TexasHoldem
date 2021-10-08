package com.epam.poker.pool;

import com.epam.poker.exception.ConnectionPoolException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
    private static final String DB_PROPERTIES_LOCATION = "prop/database.properties";
    private static String POOL_SIZE_PROPERTIES = "poolSize";
    private static final int POOL_SIZE;

    static {
        try (InputStream inputStream =
                        ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_LOCATION)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String linePoolSize = (String) properties.get(POOL_SIZE_PROPERTIES);
            POOL_SIZE = Integer.parseInt(linePoolSize);
        } catch (IOException e) {
            throw new ConnectionPoolException(e);
        }
    }

    private ConfigManager() {}

    public static int getPoolSize() {
        return POOL_SIZE;
    }
}
