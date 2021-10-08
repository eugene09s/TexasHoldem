package com.epam.poker.dao.mapper;

import com.epam.poker.model.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface as a template for mapping data from database.
 *
 * @param  <T>  type of mapping entities.
 */
public interface RowMapper<T extends Entity> {
    /**
     * Collects data from database to <T> object.
     *
     * @param  resultSet  a result set of database query.
     *
     * @return  a ready <T> object with data from database.
     */
    T map(ResultSet resultSet) throws SQLException;
}