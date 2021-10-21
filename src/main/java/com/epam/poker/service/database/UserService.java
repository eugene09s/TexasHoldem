package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface with description of operations with the User.
 */
public interface UserService {
    /**
     * Gets list users in range described as offset and amount of users.
     *
     * @param offset an amount of users to get.
     * @param amount the amount
     * @return a received list of users.
     * @throws ServiceException if database errors occurs.
     */
    List<User> findUsersRange(int offset, int amount) throws ServiceException;

    /**
     * Gets users amount in database.
     *
     * @return an amount of all users.
     * @throws ServiceException if database errors occurs.
     */
    int findUsersAmount() throws ServiceException;

    /**
     * Finds user in database by login and password and returns container of account
     * or empty container if not found.
     *
     * @param login    a login(username) of user.
     * @param password a password of user.
     * @return an optional container of user.
     * @throws ServiceException if database errors occurs.
     */
    User findUserByLoginAndPassword(String login, String password) throws ServiceException;

    /**
     * Finds user in database by login and returns container of user
     * or empty container if not found.
     *
     * @param login a login(username) of user.
     * @return an optional container of user.
     * @throws ServiceException if database errors occurs.
     */
    User findUserByLogin(String login) throws ServiceException;


    /**
     * Finds user in database by login and returns container of user
     * or empty container if not found.
     *
     * @param email a email of user.
     * @return an optional container of user.
     * @throws ServiceException if database errors occurs.
     */
    User findUserByEmail(String email) throws ServiceException;

    /**
     * Blocks user by id.
     *
     * @param id an id value of user to block.
     * @return boolean value success or fail update
     * @throws ServiceException if database errors occurs.
     */
    boolean blockById(long id) throws ServiceException;

    /**
     * Unblock account by id.
     *
     * @param id an id value of account to unblock.
     * @return boolean value success or fail update
     * @throws ServiceException if database errors occurs.
     */
    boolean unblockById(long id) throws ServiceException;

    /**
     * Adds money value to balance of user by user id.
     *
     * @param money a money value to add.
     * @param id an id value of user.
     * @return boolean value success or fail update
     * @throws ServiceException if database errors occurs.
     */
    boolean addMoneyById(BigDecimal money, long id) throws ServiceException;

    /**
     * Minus money by login boolean.
     *
     * @param money the money
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean minusMoneyByLogin(BigDecimal money, String login) throws ServiceException;

    /**
     * Add money by login boolean.
     *
     * @param money the money
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addMoneyByLogin(BigDecimal money, String login) throws ServiceException;

    /**
     * Saves specific object to database.
     * Object User should be contains UserId field
     *
     * @param item User object to update.
     * @return boolean value success or fail update
     * @throws ServiceException if database errors occurs.
     */
    boolean update(User item) throws ServiceException;

    /**
     * Add user in database and get user id.
     *
     * @param user the User
     * @return the long
     * @throws ServiceException a wrapper for lower errors.
     */
    long add(User user) throws ServiceException;

    /**
     * Update balance by login.
     *
     * @param login the login
     * @param money the money
     * @return the boolean
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean updateBalanceByLogin(String login, BigDecimal money) throws ServiceException;

    /**
     * Update balance by id.
     *
     * @param id    the id
     * @param money the money
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateBalanceById(long id, BigDecimal money) throws ServiceException;

    /**
     * Finds out if user is blocked by user id.
     *
     * @param id an id value of user to check.
     * @return boolean result of finding.
     * @throws ServiceException if user is not found and also it's a wrapper for lower errors.
     */
    boolean isBlockedById(long id) throws ServiceException;

    /**
     * Is user exist by login and password boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws ServiceException a wrapper for lower errors.
     */
    boolean isUserExistByLoginAndPassword(String login, String password) throws ServiceException;

    /**
     * Find all users from database.
     *
     * @return the list of users
     * @throws ServiceException a wrapper for lower errors.
     */
    List<User> findAll() throws ServiceException;

    /**
     * Find user by id user in database.
     *
     * @param id the id user
     * @return the user
     * @throws ServiceException a wrapper for lower errors.
     */
    User findUserById(long id) throws ServiceException;
}
