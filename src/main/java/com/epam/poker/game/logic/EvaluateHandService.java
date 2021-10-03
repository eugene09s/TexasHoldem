package com.epam.poker.game.logic;

import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Table;

public class EvaluateHandService {
    private static final EvaluateHandService instance = new EvaluateHandService();

    private EvaluateHandService() {
    }

    public static EvaluateHandService getInstance() {
        return instance;
    }

    public void execute(Table table, Gambler gambler) {

    }
}
