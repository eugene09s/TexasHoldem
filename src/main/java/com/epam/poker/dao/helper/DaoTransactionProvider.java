package com.epam.poker.dao.helper;

import com.epam.poker.dao.Dao;
import com.epam.poker.exception.DaoException;
import com.epam.poker.model.Entity;
import com.epam.poker.pool.ConnectionPool;
import com.epam.poker.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DaoTransactionProvider implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private ProxyConnection proxyConnection;

    @SafeVarargs
    public final void initTransaction(Dao<? extends Entity>... daos) throws DaoException {
        if (proxyConnection == null) {
            proxyConnection = connectionPool.getConnection();
        }
        if (daos.length > 1) {
            try {
                proxyConnection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("Init transaction error: " + e);
                throw new DaoException(e);
            }
        }
        for (Dao<? extends Entity> daoElement : daos) {
            daoElement.setConnection(proxyConnection);
        }
    }

    public void commit() throws DaoException {
        try {
            proxyConnection.commit();
        } catch (SQLException e) {
            LOGGER.error("Commit error: " + e);
            throw new DaoException(e);
        }
    }

    public void rollback() {
        try {
            proxyConnection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Rollback error: " + e);
        }
    }

    @Override
    public void close() {
        try {
            if (!proxyConnection.getAutoCommit()) {
                rollback();
            }
        } catch (SQLException e) {
           LOGGER.error("Get auto commit error: " + e);
        }
        try {
            proxyConnection.close();
        } catch (SQLException e) {
            LOGGER.error("Proxy connection error: " + e);
        }
    }
}
