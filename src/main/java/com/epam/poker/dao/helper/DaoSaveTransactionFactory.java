package com.epam.poker.dao.helper;

import com.epam.poker.connection.ConnectionPool;

public class DaoSaveTransactionFactory {

    public DaoSaveTransaction create() {
        return new DaoSaveTransaction(ConnectionPool.getInstance());
    }
}
