package com.epam.poker.model.validator.impl;

import com.epam.poker.model.validator.Validator;
import com.epam.poker.model.entity.game.GameWinner;

public class GameWinnerValidator implements Validator<GameWinner> {
    private static final int MIN_ID = 1;
    private static GameWinnerValidator instance;

    private GameWinnerValidator() {
    }

    public static GameWinnerValidator getInstance() {
        if (instance == null) {
            instance = new GameWinnerValidator();
        }
        return instance;
    }

    @Override
    public boolean isValid(GameWinner entity) {
        long gameId = entity.getGameId();
        long userid = entity.getUserId();
        if (gameId < MIN_ID || userid < MIN_ID) {
            return false;
        }
        return true;
    }
}