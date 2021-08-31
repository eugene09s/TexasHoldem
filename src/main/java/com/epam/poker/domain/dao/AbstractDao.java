package com.epam.poker.domain.dao;

import com.epam.poker.domain.mapper.RowMapper;
import com.epam.poker.exception.DaoException;
import com.epam.poker.domain.model.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements Dao<T> {
    private final Connection connection;
    private final RowMapper<T> mapper;
    private final String tableName;

    protected AbstractDao(Connection connection, RowMapper<T> mapper, String tableName) {
        this.connection = connection;
        this.mapper = mapper;
        this.tableName = tableName;
    }

    @Override
    public int findRowsAmount(Optional<String> additionalCondition) throws DaoException {
        String queryRowsAmount = "SELECT COUNT(*) FROM " + tableName;
        if (additionalCondition.isPresent()) {
            queryRowsAmount += " " + additionalCondition.get();
        }
        try (PreparedStatement statement = createStatement(queryRowsAmount)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("COUNT(*)");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

//    @Override
//    public void deleteById(long id) throws DaoException {
//        executeUpdate("DELETE FROM " + tableName + " WHERE id=" + id);
//    }
//
//    @Override
//    public Optional<T> findById(long id) throws DaoException {
//        return executeForSingleResult("SELECT * FROM " + tableName + " WHERE id=" + id);
//    }

    protected List<T> executeQuery(String query, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 1; i <= params.length; i++) {
            statement.setObject(i, params[i - 1]);
        }
        return statement;
    }

    protected Optional<T> executeForSingleResult(String query, Object... params) throws DaoException {
        List<T> items = executeQuery(query, params);
        if (items.size() == 1) {
            return Optional.of(items.get(0));
        } else if (items.size() > 1) {
            throw new IllegalArgumentException("More than one record found.");
        } else {
            return Optional.empty();
        }
    }

    protected void executeUpdate(String query) throws DaoException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected void updateSingle(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = createStatement(query, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
