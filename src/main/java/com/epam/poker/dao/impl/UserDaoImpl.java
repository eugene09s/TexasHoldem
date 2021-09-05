package com.epam.poker.dao.impl;

import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.UserDao;
import com.epam.poker.exception.DaoException;
import com.epam.poker.dao.mapper.impl.UserRowMapper;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserStatus;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static com.epam.poker.dao.ColumnName.USERS;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public static final String SQL_FIND_ALL_USERS = """
            SELECT user_id, login, password, first_name, last_name, email, 
            balance, role, status, phone_number, create_time
            FROM users 
            """;
    public static final String SQL_ADD_USER = """
            INSERT INTO users
            (login, password, first_name, last_name, email, 
            balance, role, status, phone_number, create_time)
            VALUES (?,?,?,?,?,?,?,?,?,?)
            """;
    public static final String SQL_FIND_USER_BY_ID = """
            SELECT user_id, login, first_name, last_name, email, 
            balance, role, status, phone_number, create_time
            FROM users
            WHERE user_id=?
            """;
    public static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = """
            SELECT user_id, login, first_name, last_name, email, 
            balance, role, status, phone_number, create_time
            FROM users 
            WHERE login=?
            AND password=?
            """;
    public static final String SQL_UPDATE_USER = """
            UPDATE users 
            SET login=?, first_name=?, last_name=?, email=?, 
            phone_number=?
            WHERE user_id=?
            """;
    public static final String SQL_UPDATE_PASSWORD = """
            UPDATE users 
            SET password=?
            WHERE user_id=?
            """;
    public static final String SQL_BLOCK_OR_UNBLOCK_USER = """
            UPDATE users
            SET status=?
            WHERE user_id=?
            """;
    public static final String SQL_ADD_MONEY_BALANCE = """
            UPDATE users
            SET balance=balance+?
            WHERE user_id=?
            """;
    public static final String SQL_FIND_BY_LOGIN = """
            SELECT user_id, login, first_name, last_name, email,
            balance, role, status, phone_number, create_time
            FROM users
            WHERE login=?
            """;
    public static final String SQL_FIND_BY_EMAIL = """
            SELECT user_id, login, first_name, last_name, email,
            balance, role, status, phone_number, create_time
            FROM  users
            WHERE email=?
            """;
    public static final String SQL_FIND_USERS_RANGE = """
            SELECT user_id, login, first_name, last_name, email,
            balance, role, status, phone_number, create_time
            FROM users
            WHERE role='USER'
            LIMIT ?,?
            """;

    public UserDaoImpl(Connection connection) {
        super(connection, new UserRowMapper(), USERS);
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        return executeForSingleResult(SQL_FIND_USER_BY_ID, id);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return executeQuery(SQL_FIND_ALL_USERS);
    }

    @Override
    public boolean update(User item) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_USER,
                item.getLogin(),
                item.getFirstName(),
                item.getLastName(),
                item.getEmail(),
                item.getPhoneNumber(),
                item.getUserId());
    }

    @Override
    public List<User> findUsersRange(int offset, int amount) throws DaoException {
        return executeQuery(SQL_FIND_USERS_RANGE, offset, amount);
    }

    @Override
    public int findUsersAmount() throws DaoException {
        Optional<String> additionalCondition = Optional.of("WHERE role='USER'");
        return findRowsAmount(additionalCondition);
    }

    @Override
    public Optional<User> findUserByLoginPassword(String login, String password) throws DaoException {
        return executeForSingleResult(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD,login, password);
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return executeForSingleResult(SQL_FIND_BY_LOGIN, login);
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        return executeForSingleResult(SQL_FIND_BY_EMAIL, email);
    }

    @Override
    public boolean blockById(long id) throws DaoException {
        return executeUpdateQuery(SQL_BLOCK_OR_UNBLOCK_USER,
                UserStatus.BANNED.toString(),
                id);
    }

    @Override
    public boolean unblockById(long id) throws DaoException {
        return executeUpdateQuery(SQL_BLOCK_OR_UNBLOCK_USER,
                UserStatus.ACTIVE.toString(),
                id);
    }

    @Override
    public boolean addMoneyById(BigDecimal money, long id) throws DaoException {
        return executeUpdateQuery(SQL_ADD_MONEY_BALANCE, money, id);
    }

    @Override
    public boolean updatePassword(long userId, String password) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_PASSWORD,
                password, userId);
    }

    @Override
    public long add(User user) throws DaoException {
        long generatedId = 0;
            generatedId = executeInsertQuery(SQL_ADD_USER,
                    user.getLogin(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getBalance(),
                    user.getUserRole().toString(),
                    user.getUserStatus().toString(),
                    user.getPhoneNumber(),
                    user.getCreateTime()
            );
        user.setUserId(generatedId);
        return generatedId;
    }
}
