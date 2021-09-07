package com.epam.poker.model.dao;

import com.epam.poker.model.dao.mapper.RowMapper;
import com.epam.poker.model.entity.Entity;
import com.epam.poker.exception.DaoException;

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
        setParametersInPreparedStatement(statement, params);
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

    protected boolean updateSingle(String query, Object... param) throws DaoException {
        int result = 0;
        try (PreparedStatement preparedStatement = createStatement(query, param)){
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result == 1;
    }

    protected boolean executeUpdateQuery(String query, Object... params) throws DaoException {
        int result = 0;
        try (PreparedStatement preparedStatement = createStatement(query, params)) {
            result = preparedStatement.executeUpdate();
            return result == 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected long executeInsertQuery(String query, Object... param) throws DaoException {
        long generatedId = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParametersInPreparedStatement(statement, param);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                generatedId = resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return generatedId;
    }

    private void setParametersInPreparedStatement(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 1; i <= parameters.length; i++) {
            statement.setObject(i, parameters[i - 1]);
        }
    }
}
