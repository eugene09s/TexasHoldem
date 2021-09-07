package com.epam.poker.model.logic.validator.impl;

import com.epam.poker.model.logic.validator.Validator;
import com.epam.poker.model.entity.game.Game;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GameValidator implements Validator<Game> {
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
