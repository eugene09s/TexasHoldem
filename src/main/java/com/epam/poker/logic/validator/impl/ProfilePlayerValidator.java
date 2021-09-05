package com.epam.poker.logic.validator.impl;

import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.ProfilePlayer;

import java.math.BigDecimal;

public class ProfilePlayerValidator implements Validator<ProfilePlayer> {

    @Override
    public boolean isValid(ProfilePlayer entity) {
        if (entity.getUserId() < 1) {
            return false;
        }
        BigDecimal bestPrize = entity.getBestPrize();
        BigDecimal lostMoney = entity.getLostMoney();
        BigDecimal winMoney = entity.getWinMoney();
        String aboutYourself = entity.getAboutYourselt();
        String award = entity.getAward();
        if (bestPrize == null || lostMoney == null
                || winMoney == null || aboutYourself == null
                || award == null) {
            return false;
        }
        return true;
    }
}
