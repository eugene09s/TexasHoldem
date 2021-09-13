package com.epam.poker.model.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*
*   Extending DAO interface for processing Users
 */
public interface UserDao extends Dao<User> {

    /**
    *   Gets list users in range described as offset and amount of users.
    *
    *   @param offset an amount of users to get.
    *
    *   @return a received list of users.
    *
    *   @throws DaoException if database errors occurs.
     */
    List<User> findUsersRange(int offset, int amount) throws DaoException;

    /**
     * Gets users amount in database.
     *
     * @return  an amount of all users.
     *
     * @throws  DaoException  if database errors occurs.
     */
    int findUsersAmount() throws DaoException;

    /**
     * Finds user in database by login and password and returns container of account
     * or empty container if not found.
     *
     * @param  login     a login(username) of user.
     * @param  password  a password of user.
     *
     * @return  an optional container of account.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<User> findUserByLoginPassword(String login, String password) throws DaoException;

    /**
     * Finds user in database by login and returns container of account
     * or empty container if not found.
     *
     * @param  login  a login(username) of account.
     *
     * @return  an optional container of account.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Finds user in database by login and returns container of account
     * or empty container if not found.
     *
     * @param  email  a email of user.
     *
     * @return  an optional container of user.
     *
     * @throws  DaoException  if database errors occurs.
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Blocks user by id.
     *
     * @param  id  an id value of user to block.
     *
     * @return boolean value successe or fail update
     *
     * @throws  DaoException  if database errors occurs.
     */
    boolean blockById(long id) throws DaoException;

    /**
     * Unblock account by id.
     *
     * @return boolean value successe or fail update
     *
     * @param  id  an id value of account to unblock.
     *
     * @throws  DaoException  if database errors occurs.
     */
    boolean unblockById(long id) throws DaoException;

    /**
     * Adds money value to balance of user by user id.
     *
     * @param  money  a money value to add.
     * @param  id     an id value of account.
     *
     * @return boolean value successe or fail update
     *
     * @throws  DaoException  if database errors occurs.
     */
    boolean addMoneyById(BigDecimal money, long id) throws DaoException;

    /**
     * Saves specific object to database.
     * Object User should be conatin UserId field
     *
     * @param  item  User object to update.
     *
     * @return boolean value successe or fail update
     *
     * @throws  DaoException  if database errors occurs.
     */
    boolean update(User item) throws DaoException;
    long add(User t) throws DaoException;
    boolean updatePassword(long userId, String password) throws DaoException;
}
