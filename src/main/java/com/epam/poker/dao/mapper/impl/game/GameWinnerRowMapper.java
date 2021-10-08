package com.epam.poker.dao.mapper.impl.game;

import com.epam.poker.dao.mapper.RowMapper;
import com.epam.poker.model.database.game.GameWinner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class GameWinnerRowMapper implements RowMapper<GameWinner> {
    private static GameWinnerRowMapper instance;

    private GameWinnerRowMapper() {
    }

    public static GameWinnerRowMapper getInstance() {
        if (instance == null) {
            instance = new GameWinnerRowMapper();
        }
        return instance;
    }

    @Override
    public GameWinner map(ResultSet resultSet) throws SQLException {
        return GameWinner.builder()
                .setGameWinnerId(resultSet.getLong(GAME_WINNER_ID))
                .setGameId(resultSet.getLong(GAME_WINNER_GAME_ID))
                .setUserId(resultSet.getLong(GAME_WINNER_USER_ID))
                .createGameWinner();
    }
}
