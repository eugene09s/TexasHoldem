package com.epam.poker.dao.mapper.impl;

import com.epam.poker.dao.mapper.RowMapper;
import com.epam.poker.model.entity.ProfilePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class ProfilePlayerRowMapper implements RowMapper<ProfilePlayer> {
    @Override
    public ProfilePlayer map(ResultSet resultSet) throws SQLException {
        return ProfilePlayer.builder()
                .setUserId(resultSet.getLong(PROFILE_PLAYER_USER_ID))
                .setBestPrize(resultSet.getBigDecimal(PROFILE_PLAYER_BEST_PRIZE))
                .setAward(resultSet.getString(PROFILE_PLAYER_AWARD))
                .setPhoto(resultSet.getString(PROFILE_PLAYER_PHOTO))
                .setAboutYourself(resultSet.getString(PROFILE_PLAYER_ABOUT_YOURSELF))
                .setLostMoney(resultSet.getBigDecimal(PROFILE_PLAYER_LOST_MONEY))
                .setWinMoney(resultSet.getBigDecimal(PROFILE_PLAYER_WIN_MONEY))
                .createRatingPlayer();
    }
}
