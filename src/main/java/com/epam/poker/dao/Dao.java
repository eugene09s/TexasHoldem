package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.Entity;

import java.util.List;
import java.util.Optional;

/**
 * Interface as a template for accessing to entity data in database.
 * Contains CRUD methods.
 *
 * @param  <T>  type of handling entities.
 */
public interface Dao<T extends Entity> {
    /**
     * Finds specific object by id and returns container of object if exist
     * or empty container if not.
     *
     * @param  id  an id value of object to search.
     *
     * @return  an optional container of <T> object.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<T> findById(long id) throws DaoException;

    /**
     * Gets list of all available objects.
     *
     * @return  a list of all available <T> objects in database.
     *
     * @throws  DaoException  if database errors occurs.
     */
    List<T> findAll() throws DaoException;

    /**
     * Saves specific object to database.
     *
     * @param  item  <T> object to update.
     *
     * @return boolean value successe or fail update
     *
     * @throws  DaoException  if database errors occurs.
     */
    boolean update(T item) throws DaoException;

    /**
     * Gets rows amount in specific table.
     *
     * @param  additionalCondition  an optional condition to query presented in string.
     *
     * @return  a rows amount in the table.
     *
     * @throws  DaoException  if database errors occurs.
     */
    int findRowsAmount(Optional<String> additionalCondition) throws DaoException;
    long add(T t) throws DaoException;
}