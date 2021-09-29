package com.epam.poker.model.service.user;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface with description of operations with the User.
 */
public interface UserService {
    /**
     *   Gets list users in range described as offset and amount of users.
     *
     *   @param offset an amount of users to get.
     *
     *   @return a received list of users.
     *
     *   @throws ServiceException if database errors occurs.
     */
    List<User> findUsersRange(int offset, int amount) throws ServiceException, DaoException;

    /**
     * Gets users amount in database.
     *
     * @return  an amount of all users.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    int findUsersAmount() throws ServiceException, DaoException;

    /**
     * Finds user in database by login and password and returns container of account
     * or empty container if not found.
     *
     * @param  login     a login(username) of user.
     * @param  password  a password of user.
     *
     * @return  an optional container of account.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    User findUserByLoginPassword(String login, String password) throws ServiceException, DaoException;

    /**
     * Finds user in database by login and returns container of account
     * or empty container if not found.
     *
     * @param  login  a login(username) of account.
     *
     * @return  an optional container of account.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    User findUserByLogin(String login) throws ServiceException, DaoException;


    /**
     * Finds user in database by login and returns container of account
     * or empty container if not found.
     *
     * @param  email  a email of user.
     *
     * @return  an optional container of user.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    User findUserByEmail(String email) throws ServiceException, DaoException;

    /**
     * Blocks user by id.
     *
     * @param  id  an id value of user to block.
     *
     * @return boolean value successe or fail update
     *
     * @throws  ServiceException  if database errors occurs.
     */
    boolean blockById(long id) throws ServiceException, DaoException;

    /**
     * Unblock account by id.
     *
     * @return boolean value successe or fail update
     *
     * @param  id  an id value of account to unblock.
     *
     * @throws  ServiceException  if database errors occurs.
     */
    boolean unblockById(long id) throws ServiceException, DaoException;

    /**
     * Adds money value to balance of user by user id.
     *
     * @param  money  a money value to add.
     * @param  id     an id value of account.
     *
     * @return boolean value successe or fail update
     *
     * @throws  ServiceException  if database errors occurs.
     */
    boolean addMoneyById(BigDecimal money, long id) throws ServiceException, DaoException;
    boolean minusMoneyByLogin(BigDecimal money, String login) throws ServiceException, DaoException;
    boolean addMoneyByLogin(BigDecimal money, String login) throws ServiceException, DaoException;
    /**
     * Saves specific object to database.
     * Object User should be conatin UserId field
     *
     * @param  item  User object to update.
     *
     * @return boolean value successe or fail update
     *
     * @throws  ServiceException  if database errors occurs.
     */
    boolean update(User item) throws ServiceException, DaoException;
    long add(User t) throws ServiceException, DaoException;
    boolean updatePassword(long userId, String password) throws ServiceException, DaoException;

    /**
     * Finds out if user is blocked by user id.
     *
     * @param  id  an id value of user to check.
     *
     * @return  boolean result of finding.
     *
     * @throws ServiceException  if user is not found and
     *                            also it's a wrapper for lower errors.
     */
    boolean isBlockedById(long id) throws ServiceException, DaoException;
    boolean isUserExistByLoginPassword(String login, String password) throws ServiceException, DaoException;
    List<User> findAll() throws ServiceException, DaoException;
    User findUserById(long id) throws ServiceException, DaoException;
}
