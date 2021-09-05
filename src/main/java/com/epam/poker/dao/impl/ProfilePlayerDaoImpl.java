package com.epam.poker.dao.impl;

import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.exception.DaoException;
import com.epam.poker.dao.mapper.impl.ProfilePlayerRowMapper;
import com.epam.poker.model.entity.ProfilePlayer;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static com.epam.poker.dao.ColumnName.PROFILE_PLAYERS;

public class ProfilePlayerDaoImpl extends AbstractDao<ProfilePlayer> implements ProfilePlayerDao {
    public static final String SQL_ADD_PROFILE_PLAYER = """
            INSERT INTO profile_players
            (player_id, best_prize, award, photo, about_yourself, lost_money, win_money)
            VALUES (?,?,?,?,?,?,?)
            """;
    public static final String SQL_FIND_ALL_PROFILE_PLAYERS = """
            SELECT player_id, best_prize, award, photo, about_yourself, lost_money, win_money
            FROM profile_players
            """;
    public static final String SQL_FIND_PROFILE_PLAYER_BY_USER_ID = """
            SELECT player_id, best_prize, award, photo, about_yourself, lost_money, win_money
            FROM profile_players
            WHERE player_id =?
            """;
    public static final String SQL_UPDATE_PHOTO_BY_USER_ID = """
            UPDATE profile_players
            SET photo=?
            WHERE player_id=?
            """;
    public static final String SQL_UPDATE_ABOUT_YOURSELF_BY_USER_ID = """
            UPDATE profile_players
            SET about_yourself=?
            WHERE player_id=?
            """;
    public static final String SQL_UPDATE_BEST_PRIZE_BY_USER_ID = """
            UPDATE profile_players
            SET best_prize=?
            WHERE ? > best_prize AND player_id=?
            """;
    public static final String SQL_UPDATE_AWARD_BY_USER_ID = """
            UPDATE profile_players
            SET award=?
            WHERE player_id=?
            """;
    public static final String SQL_UPDATE_LOST_MONEY_BY_USER_ID = """
            UPDATE profile_players
            SET lost_money=lost_money+?
            WHERE player_id=?
            """;
    public static final String SQL_UPDATE_WIN_MONEY_BY_USER_ID = """
            UPDATE profile_players
            SET win_money=win_money+?
            WHERE player_id=?
            """;
    public static final String SQL_UPDATE_PROFILE_PLAYER_BY_USER_ID = """
            UPDATE profile_players
            SET best_prize=?, award=?, photo=?, about_yourself=?, lost_money=?, win_money=?
            WHERE player_id=?
            """;

    public ProfilePlayerDaoImpl(Connection connection) {
        super(connection, new ProfilePlayerRowMapper(), PROFILE_PLAYERS);
    }

    @Override
    public Optional<ProfilePlayer> findById(long id) throws DaoException {
        return executeForSingleResult(SQL_FIND_PROFILE_PLAYER_BY_USER_ID, id);
    }

    @Override
    public List<ProfilePlayer> findAll() throws DaoException {
        return executeQuery(SQL_FIND_ALL_PROFILE_PLAYERS);
    }

    @Override
    public int findProfilePlayerAmount() throws DaoException {
        Optional<String> additionalCondition = Optional.empty();
        return findRowsAmount(additionalCondition);
    }

    @Override
    public boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_PROFILE_PLAYER_BY_USER_ID,
                profilePlayer.getBestPrize(),
                profilePlayer.getAward(),
                profilePlayer.getPhoto(),
                profilePlayer.getAboutYourselt(),
                profilePlayer.getLostMoney(),
                profilePlayer.getWinMoney(),
                userId);
    }

    @Override
    public boolean updatePhotoByUserId(long userId, Blob photo) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_PHOTO_BY_USER_ID, photo, userId);
    }

    @Override
    public boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_BEST_PRIZE_BY_USER_ID, bestPrize, bestPrize, userId);
    }

    @Override
    public boolean updateAwardByUserId(long userId, String award) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_AWARD_BY_USER_ID, award, userId);
    }

    @Override
    public boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_ABOUT_YOURSELF_BY_USER_ID, aboutYourself, userId);
    }

    @Override
    public boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_LOST_MONEY_BY_USER_ID, money, userId);
    }

    @Override
    public boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws DaoException {
        return executeUpdateQuery(SQL_UPDATE_WIN_MONEY_BY_USER_ID, money, userId);
    }

    @Override
    public void add(ProfilePlayer profilePlayer) throws DaoException {
         updateSingle(SQL_ADD_PROFILE_PLAYER,
                profilePlayer.getUserId(),
                profilePlayer.getBestPrize(),
                profilePlayer.getAward(),
                profilePlayer.getPhoto(),
                profilePlayer.getAboutYourselt(),
                profilePlayer.getLostMoney(),
                profilePlayer.getWinMoney());
    }
}
