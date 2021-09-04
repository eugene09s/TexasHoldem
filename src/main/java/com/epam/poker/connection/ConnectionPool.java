package com.epam.poker.connection;

import com.epam.poker.exception.ConnectionPoolException;
import com.epam.poker.exception.DaoException;
import org.apache.logging.log4j.core.net.UrlConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final AtomicBoolean IS_POOL_CREATED = new AtomicBoolean(false);
    private static final ReentrantLock INSTANCE_LOCKER = new ReentrantLock();
    private static final ReentrantLock CONNECTION_LOCKER = new ReentrantLock();
    private static final int POOL_SIZE = ConfigManager.getPoolSize();
    private static final Semaphore SEMAPHORE = new Semaphore(POOL_SIZE, true);
    private static ConnectionPool instance = null;
    private final Queue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> busyConnections;
    private final ConnectionCreator connectionCreator = new ConnectionCreator();

    private ConnectionPool() {
        freeConnections = new ArrayDeque<>();
        busyConnections = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; ++i) {
                Connection connection = connectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                freeConnections.add(proxyConnection);
        }
    }

    public static ConnectionPool getInstance() {
        if (!IS_POOL_CREATED.get()) {
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
