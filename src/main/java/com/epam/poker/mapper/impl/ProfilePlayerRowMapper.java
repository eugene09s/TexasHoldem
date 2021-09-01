package com.epam.poker.mapper.impl;

import com.epam.poker.mapper.RowMapper;
import com.epam.poker.model.entity.ProfilePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class ProfilePlayerRowMapper implements RowMapper<ProfilePlayer> {
    @Override
    public ProfilePlayer map(ResultSet resultSet) throws SQLException {
        return ProfilePlayer.builder()
                .setUserId(resultSet.getLong(PROFILE_PLAYER_USER_ID))
                .setRanking(resultSet.getBigDecimal(PROFILE_PLAYER_RANKING))
                .setBestPrize(resultSet.getBigDecimal(PROFILE_PLAYER_BEST_PRIZE))
                .setAward(resultSet.getString(PROFILE_PLAYER_AWARD))
                .setPhoto(resultSet.getBlob(PROFILE_PLAYER_PHOTO))
                .setAboutYourself(resultSet.getString(PROFILE_PLAYER_ABOUT_YOURSELF))
                .createRatingPlayer();
    }
}
