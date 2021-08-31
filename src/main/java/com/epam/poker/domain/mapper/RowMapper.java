/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.epam.poker.domain.mapper;

import com.epam.poker.domain.model.Entity;

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