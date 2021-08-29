package com.epam.poker.connection;

import com.epam.poker.exception.ConnectionPoolException;
import com.epam.poker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicBoolean IS_POOL_CREATED = new AtomicBoolean(false);
    private static final ReentrantLock INSTANCE_LOCKER = new ReentrantLock();
    private static final ReentrantLock CONNECTION_LOCKER = new ReentrantLock();
    private static String POOL_SIZE_PROPERTIES = "poolSize";
    private static String PROPERTIES_FILE_PATH = "properties.properties";
    private static int poolSize;
    private static Semaphore SEMAPHORE;
    private static ConnectionPool instance = null;
    private final Queue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> busyConnections;

    public ConnectionPool() {
        try (InputStream inputStream =
                     ConnectionCreator.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH);) {
            Properties properties = null;
            properties.load(inputStream);
            String linePoolSize = (String) properties.get(POOL_SIZE_PROPERTIES);
            this.poolSize = Integer.parseInt(linePoolSize);
        } catch (IOException e) {
            LOGGER.fatal(e);
            throw new ConnectionPoolException(e);
        }
        SEMAPHORE = new Semaphore(poolSize, true);
        freeConnections = new ArrayDeque<>();
        busyConnections = new ArrayDeque<>();
        for (int i = 0; i < poolSize; ++i) {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                freeConnections.add(proxyConnection);
        }
    }

    public static ConnectionPool getInstance() {
        if (IS_POOL_CREATED.get()) {
            INSTANCE_LOCKER.lock();
            try {
                if (!IS_POOL_CREATED.get()) {
                    instance = new ConnectionPool();
                    IS_POOL_CREATED.set(true);
                }
            } finally {
                INSTANCE_LOCKER.unlock();
            }
        }
        return instance;
    }

    public void releaseConnection(ProxyConnection proxyConnection) {
        CONNECTION_LOCKER.lock();
        try {
            if (busyConnections.contains(proxyConnection)) {
                freeConnections.offer(proxyConnection);
                busyConnections.poll();
                SEMAPHORE.release();
            }
        } finally {
            CONNECTION_LOCKER.unlock();
        }
    }

    public ProxyConnection getConnection() {
        try {
            SEMAPHORE.acquire();
            CONNECTION_LOCKER.lock();
            ProxyConnection currentConnection = freeConnections.poll();
            busyConnections.offer(currentConnection);
            return currentConnection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        } finally {
            CONNECTION_LOCKER.unlock();
        }
    }

    public void closeAll() throws DaoException {
        freeConnections.addAll(busyConnections);
        busyConnections.clear();
        for (ProxyConnection proxyConnection : freeConnections) {
            try {
                proxyConnection.finalCloseConnection();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}
