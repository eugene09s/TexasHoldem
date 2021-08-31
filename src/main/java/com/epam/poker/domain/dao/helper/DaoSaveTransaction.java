package com.epam.poker.domain.dao.helper;

import com.epam.poker.connection.ConnectionPool;
import com.epam.poker.connection.ProxyConnection;
import com.epam.poker.domain.dao.UserDao;
import com.epam.poker.domain.dao.impl.UserDaoImpl;
import com.epam.poker.exception.DaoException;

import java.sql.SQLException;

public class DaoSaveTransaction implements AutoCloseable{
    private ProxyConnection connection;

    public DaoSaveTransaction(ConnectionPool pool) {
        this.connection = pool.getConnection();
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new DaoException(rollbackException);
            }
        }
    }

    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}
