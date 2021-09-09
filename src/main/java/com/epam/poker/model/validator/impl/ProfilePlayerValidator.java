package com.epam.poker.model.validator.impl;

import com.epam.poker.model.validator.Validator;
import com.epam.poker.model.entity.ProfilePlayer;

import java.math.BigDecimal;

public class ProfilePlayerValidator implements Validator<ProfilePlayer> {
    private static final long MIN_USER_ID = 1;
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
        if (entity.getUserId() < MIN_USER_ID) {
            return false;
        }
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
