package com.epam.poker.dao.impl.game;

import com.epam.poker.dao.AbstractDao;
import com.epam.poker.dao.GameWinnerDao;
import com.epam.poker.dao.mapper.impl.GameWinnerRowMapper;
import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.game.GameWinner;

import java.util.List;
import java.util.Optional;

import static com.epam.poker.dao.ColumnName.GAME_WINNERS;

public class GameWinnerDaoImpl extends AbstractDao<GameWinner> implements GameWinnerDao {
    public static final String SQL_FIND_ALL_GAME_WINNERS = """
            SELECT game_winner_id, game_id, game_winner_user_id
            FROM game_winners
            """;
    public static final String SQL_ADD_GAME_WINNER = """
            INSERT INTO game_winners
            (game_winner_id, game_id, game_winner_user_id)
            VALUES (?,?,?)
            """;
    public static final String SQL_FIND_GAME_WINNER_BY_USER_ID = """
            SELECT game_winner_id, game_id, game_winner_user_id
            FROM game_winners
            WHERE game_winner_user_id=?
            """;
    public static final String SQL_FIND_GAME_WINNER_BY_GAME_ID = """
            SELECT game_winner_id, game_id, game_winner_user_id
            FROM game_winners
            WHERE game_id=?
            """;
    public static final String SQL_FIND_GAME_WINNER_BY_GAME_WINNER_ID = """
            SELECT game_winner_id, game_id, game_winner_user_id
            FROM game_winners
            WHERE game_winner_id=?
            """;

    public GameWinnerDaoImpl() {
        super(GameWinnerRowMapper.getInstance(), GAME_WINNERS);
    }

    @Override
    public Optional<GameWinner> findById(long id) throws DaoException {
        return executeForSingleResult(SQL_FIND_GAME_WINNER_BY_GAME_WINNER_ID, id);
    }

    @Override
    public List<GameWinner> findAll() throws DaoException {
        return executeQuery(SQL_FIND_ALL_GAME_WINNERS);
    }

    @Override
    public int findGameWinnerAmount() throws DaoException {
        Optional<String> additionalCondition = Optional.empty();
        return findRowsAmount(additionalCondition);
    }

    @Override
    public long add(GameWinner gameWinner) throws DaoException {
        return executeInsertQuery(SQL_ADD_GAME_WINNER,
                gameWinner.getGameWinnerId(),
                gameWinner.getGameId(),
                gameWinner.getUserId());
    }

    @Override
    public List<GameWinner> findGameWinnersByGameId(long gameId) throws DaoException {
        return executeQuery(SQL_FIND_GAME_WINNER_BY_GAME_ID, gameId);
    }

    @Override
    public List<GameWinner> findGameWinnerByUserId(long userId) throws DaoException {
        return executeQuery(SQL_FIND_GAME_WINNER_BY_USER_ID, userId);
    }
}
