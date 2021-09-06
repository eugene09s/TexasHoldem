package com.epam.poker.logic.validator.impl;

import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.game.GamePlayer;

public class GamePlayerValidator implements Validator<GamePlayer> {
    private static final int MIN_ID = 1;

    @Override
    public boolean isValid(GamePlayer entity) {
        String lastAction = entity.getLastAction();
        String twoCards = entity.getTwoCards();
        String combinationsCards = entity.getCombinationsCards();
        if (lastAction == null || twoCards == null || combinationsCards == null) {
            return false;
        }
        long userId = entity.getUserId();
        long gameId = entity.getGameId();
        if (userId < MIN_ID || gameId < MIN_ID) {
            return false;
        }
        return true;
    }
}
