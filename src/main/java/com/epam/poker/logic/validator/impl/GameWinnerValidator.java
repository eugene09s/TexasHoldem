package com.epam.poker.logic.validator.impl;

import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.game.GameWinner;

public class GameWinnerValidator implements Validator<GameWinner> {
    @Override
    public boolean isValid(GameWinner entity) {
        long gameId = entity.getGameId();
        long userid = entity.getUserId();
        if (gameId < 1 || userid < 1) {
            return false;
        }
        return true;
    }
}