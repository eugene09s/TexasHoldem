package com.epam.poker.dao.mapper.impl;

import com.epam.poker.dao.mapper.RowMapper;
import com.epam.poker.model.entity.RatingPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class RatingPlayerRowMapper implements RowMapper<RatingPlayer> {
    @Override
    public RatingPlayer map(ResultSet resultSet) throws SQLException {
        RatingPlayer ratingPlayer = RatingPlayer.builder()
                .setUserId(resultSet.getLong(RATING_PLAYER_USER_ID))
                .setRanking(resultSet.getBigDecimal(RATING_PLAYER_RANKING))
                .setBestPrize(resultSet.getBigDecimal(RATING_PLAYER_BEST_PRIZE))
                .setAward(resultSet.getString(RATING_PLAYER_AWARD))
                .createRatingPlayer();
        return ratingPlayer;
    }
}
