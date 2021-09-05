package com.epam.poker.logic.validator.impl;

import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.game.GamePlayer;

public class GamePlayerValidator implements Validator<GamePlayer> {
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
        if (userId > 1 || gameId > 1) {
            return false;
        }
        return true;
    }
}
