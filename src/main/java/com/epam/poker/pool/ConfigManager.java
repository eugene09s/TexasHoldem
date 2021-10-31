package com.epam.poker.pool;

import com.epam.poker.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sonatype.aether.util.listener.AbstractTransferListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public final class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DB_PROPERTIES_LOCATION = "prop/database.properties";
    private static final int POOL_SIZE;

    static {
        Map<String, String> environmentVariable = System.getenv();
        String strPoolSize = environmentVariable.get("POOL_SIZE");
        if (strPoolSize == null) {
            try (InputStream inputStream =
                         ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_LOCATION)) {
                Properties properties = new Properties();
                properties.load(inputStream);
                String poolSizeProperties = "poolSize";
                String linePoolSize = (String) properties.get(poolSizeProperties);
                POOL_SIZE = Integer.parseInt(linePoolSize);
            } catch (IOException e) {
                throw new ConnectionPoolException(e);
            }
            LOGGER.info("Read default config pool size: " + POOL_SIZE);
        } else {
            POOL_SIZE = Integer.parseInt(strPoolSize);
            LOGGER.info("Read environment variables pool size: " + POOL_SIZE);
        }
    }

    private ConfigManager() {}

    public static int getPoolSize() {
        return POOL_SIZE;
    }
}
