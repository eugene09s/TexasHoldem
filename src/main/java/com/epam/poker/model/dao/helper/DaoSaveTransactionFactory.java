package com.epam.poker.model.dao.helper;

import com.epam.poker.model.pool.ConnectionPool;

public class DaoSaveTransactionFactory {

    public DaoSaveTransaction create() {
        return new DaoSaveTransaction(ConnectionPool.getInstance());
    }
}
