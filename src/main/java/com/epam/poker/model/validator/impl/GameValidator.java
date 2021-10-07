package com.epam.poker.model.validator.impl;

import com.epam.poker.model.validator.Validator;
import com.epam.poker.model.entity.database.game.Game;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GameValidator implements Validator<Game> {
    private static GameValidator instance;

    private GameValidator() {
    }

    public static GameValidator getInstance() {
        if (instance == null) {
            instance = new GameValidator();
        }
        return instance;
    }

    @Override
    public boolean isValid(Game entity) {
        String title = entity.getTitle();
        Timestamp date = entity.getDate();
        BigDecimal bank = entity.getBank();
        String fiveCards = entity.getFiveCards();
        if (title == null || date == null
                || bank == null || fiveCards == null) {
            return false;
        }
        return true;
    }
}
