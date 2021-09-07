package com.epam.poker.model.logic.validator.impl;

import com.epam.poker.model.logic.validator.Validator;
import com.epam.poker.model.entity.game.GameWinner;

public class GameWinnerValidator implements Validator<GameWinner> {
    private static final int MIN_ID = 1;

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