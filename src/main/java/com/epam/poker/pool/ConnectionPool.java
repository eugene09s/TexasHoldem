package com.epam.poker.pool;

import com.epam.poker.exception.ConnectionPoolException;
import com.epam.poker.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static final ReentrantLock instanceLocker = new ReentrantLock();
    private static final ReentrantLock connectionLocker = new ReentrantLock();
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
        if (!isPoolCreated.get()) {
            instanceLocker.lock();
            try {
                if (!isPoolCreated.get()) {
                    instance = new ConnectionPool();
                    isPoolCreated.set(true);
                }
            } finally {
                instanceLocker.unlock();
            }
        }
        return instance;
    }

    public void releaseConnection(ProxyConnection proxyConnection) {
        connectionLocker.lock();
        try {
            if (busyConnections.contains(proxyConnection)) {
                freeConnections.offer(proxyConnection);
                busyConnections.poll();
                SEMAPHORE.release();
            }
        } finally {
            connectionLocker.unlock();
        }
    }

    public ProxyConnection getConnection() {
        try {
            SEMAPHORE.acquire();
            connectionLocker.lock();
            ProxyConnection currentConnection = freeConnections.poll();
            busyConnections.offer(currentConnection);
            return currentConnection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        } finally {
            connectionLocker.unlock();
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
