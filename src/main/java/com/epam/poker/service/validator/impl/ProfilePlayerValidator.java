package com.epam.poker.service.validator.impl;

import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.service.validator.Validator;

import java.math.BigDecimal;

public class ProfilePlayerValidator implements Validator<ProfilePlayer> {
    private static final int MAX_LENGTH_ABOUT_YOURSELF = 512;
    private static final int MAX_LENGTH_AWARD = 128;
    private static final int MAX_LENGTH_PHOTO = 128;
    private static ProfilePlayerValidator instance;

    private ProfilePlayerValidator() {
    }

    public static ProfilePlayerValidator getInstance() {
        if (instance == null) {
            instance = new ProfilePlayerValidator();
        }
        return instance;
    }

    @Override
    public boolean isValid(ProfilePlayer entity) {
        BigDecimal bestPrize = entity.getBestPrize();
        BigDecimal lostMoney = entity.getLostMoney();
        BigDecimal winMoney = entity.getWinMoney();
        String aboutYourself = entity.getAboutYourself();
        String award = entity.getAward();
        String photo = entity.getPhoto();
        if (bestPrize == null || lostMoney == null
                || winMoney == null || aboutYourself == null
                || award == null) {
            return false;
        }
        if (aboutYourself.length() > MAX_LENGTH_ABOUT_YOURSELF
                || award.length() > MAX_LENGTH_AWARD
                || photo.length() > MAX_LENGTH_PHOTO) {
            return false;
        }
        return true;
    }
}
