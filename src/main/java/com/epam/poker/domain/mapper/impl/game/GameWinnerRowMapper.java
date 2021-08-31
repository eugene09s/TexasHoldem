package com.epam.poker.domain.mapper.impl.game;

import com.epam.poker.domain.mapper.RowMapper;
import com.epam.poker.domain.model.entity.game.GameWinner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.domain.dao.ColumnName.*;

public class GameWinnerRowMapper implements RowMapper<GameWinner> {
    @Override
    public GameWinner map(ResultSet resultSet) throws SQLException {
        return GameWinner.builder()
                .setGameWinnerId(resultSet.getLong(GAME_WINNER_ID))
                .setGameId(resultSet.getLong(GAME_WINNER_GAME_ID))
                .setUserId(resultSet.getLong(GAME_WINNER_USER_ID))
                .createGameWinner();
    }
}
