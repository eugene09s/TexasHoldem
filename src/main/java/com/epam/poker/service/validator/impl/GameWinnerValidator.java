package com.epam.poker.service.validator.impl;

import com.epam.poker.model.database.game.GameWinner;
import com.epam.poker.service.validator.Validator;

public class GameWinnerValidator implements Validator<GameWinner> {
    private static final int MIN_ID = 0;
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
        long userid = entity.getUserId();
        if (userid < MIN_ID) {
            return false;
        }
        return true;
    }
}