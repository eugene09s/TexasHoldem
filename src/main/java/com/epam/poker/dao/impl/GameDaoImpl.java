package com.epam.poker.dao.impl;

import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.GameDao;
import com.epam.poker.exception.DaoException;
import com.epam.poker.mapper.RowMapper;
import com.epam.poker.mapper.impl.game.GameRowMapper;
import com.epam.poker.model.entity.game.Game;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static com.epam.poker.dao.ColumnName.GAMES;

public class GameDaoImpl extends AbstractDao<Game> implements GameDao {
    public static final String SQL_FIND_ALL_GAMES = """
            SELECT
            game_id, title, date, bank, five_cards
            FROM
            games
            """;

    public static final String SQL_ADD_GAME = """
            INSERT INTO
            games
            (title, date, bank, five_cards)
            VALUES (?,?,?,?)
            """;

    public static final String SQL_FIND_GAME_BY_ID = """
            SELECT
            game_id, title, date, bank, five_cards
            FROM
            games
            WHERE
            game_id=?
            """;

    protected GameDaoImpl(Connection connection, RowMapper<Game> mapper, String tableName) {
        super(connection, new GameRowMapper(), GAMES);
    }

    @Override
    public List<Game> findAll() throws DaoException {
        return executeQuery(SQL_FIND_ALL_GAMES);
    }

    @Override
    public long add(Game game) throws DaoException {
        return executeInsertQuery(SQL_ADD_GAME,
                game.getTitle(),
                game.getDate(),
                game.getBank(),
                game.getFiveCards());
    }

    @Override
    public int findGameAmount() throws DaoException {
        Optional<String> additionalCondition = Optional.of("");
        return findRowsAmount(additionalCondition);
    }

    @Override
    public Optional<Game> findById(long id) throws DaoException {
        return executeForSingleResult(SQL_FIND_GAME_BY_ID, id);
    }
}
