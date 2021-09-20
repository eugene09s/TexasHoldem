package com.epam.poker.model.dao.helper;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.dao.AbstractDao;
import com.epam.poker.model.pool.ConnectionPool;
import com.epam.poker.model.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DaoSaveTransaction implements AutoCloseable{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private ProxyConnection proxyConnection;

    public void initTransaction(AbstractDao dao, AbstractDao... daos) throws DaoException {
        if (proxyConnection == null) {
            proxyConnection = connectionPool.getConnection();
        }
        try {
            proxyConnection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Init transaction error: " + e);
            throw new DaoException(e);
        }
        dao.setConnection(proxyConnection);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(proxyConnection);
        }
    }

    public void init(AbstractDao dao) {
        if (proxyConnection == null) {
            proxyConnection = connectionPool.getConnection();
        }
        dao.setConnection(proxyConnection);
    }

    public void endTransaction() throws DaoException {
        if (proxyConnection == null) {
            LOGGER.error("Connection equal null!");
            throw new DaoException("Connection don't close");
        }
        try {
            proxyConnection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("End transaction. Set auto commit error: " + e);
            throw new DaoException(e);
        }
        connectionPool.releaseConnection(proxyConnection);
        proxyConnection = null;
    }

    public void end() throws DaoException {
        if (proxyConnection == null) {
            LOGGER.error("Connection equal null!");
            throw new DaoException("Connection don't close");
        }
        connectionPool.releaseConnection(proxyConnection);
        proxyConnection = null;
    }

    public void rollback() throws DaoException {
        try {
            proxyConnection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Rollback error: " + e);
            throw new DaoException(e);
        }
    }

    public void commit() throws DaoException {
        try {
            proxyConnection.commit();
        } catch (SQLException e) {
            LOGGER.error("Commit error close: " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public void close() throws DaoException, SQLException {
        if (proxyConnection == null) {
            LOGGER.error("Connection equal null!");
            throw new DaoException("Connection don't close");
        }
        if (!proxyConnection.getAutoCommit()) {
            try {
                proxyConnection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error("End transaction. Set auto commit error: " + e);
                throw new DaoException(e);
            }
        }
        connectionPool.releaseConnection(proxyConnection);
        proxyConnection = null;
    }
}
