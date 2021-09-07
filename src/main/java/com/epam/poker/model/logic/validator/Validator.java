package com.epam.poker.model.logic.validator;

import com.epam.poker.model.entity.Entity;

/**
 * Interface as template of <T> object validator.
 *
 * @param <T> type of entities of validate.
 */
public interface Validator<T extends Entity> {

    /**
     * Validates given <T> object and returns boolean result.
     *
     * @param entity <T> object to validate.
     *
     * @return boolean result of validate.
     */
    boolean isValid(T entity);
}
