package com.epam.poker.service.validator.impl;

import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.service.validator.Validator;

public class GamePlayerValidator implements Validator<GamePlayer> {
    private static final int MIN_ID = 0;
    private static GamePlayerValidator instance;

    private GamePlayerValidator() {
    }

    public static GamePlayerValidator getInstance() {
        if (instance == null) {
            instance = new GamePlayerValidator();
        }
        return instance;
    }

    @Override
    public boolean isValid(GamePlayer entity) {
        String lastAction = entity.getLastAction();
        String twoCards = entity.getTwoCards();
//        String combinationsCards = entity.getCombinationsCards();
        if (lastAction == null || twoCards == null) {
            return false;
        }
        long userId = entity.getUserId();
        if (userId < MIN_ID) {
            return false;
        }
        return true;
    }
}
