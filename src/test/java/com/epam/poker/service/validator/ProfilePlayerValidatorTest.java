package com.epam.poker.service.validator;

import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.service.validator.impl.ProfilePlayerValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfilePlayerValidatorTest {
    private static final ProfilePlayerValidator profilePlayerValidator = ProfilePlayerValidator.getInstance();
    private static final ProfilePlayer VALID_PROFILE_PLAYER = ProfilePlayer.builder()
            .setBestPrize(BigDecimal.ZERO)
            .setLostMoney(BigDecimal.ZERO)
            .setWinMoney(BigDecimal.ZERO)
            .setAboutYourself("Hello")
            .setAward("Nothing")
            .setPhoto("my photo")
            .createRatingPlayer();

    @Test
    public void testValidProfilePlayer() {
        boolean actual = profilePlayerValidator.isValid(VALID_PROFILE_PLAYER);
        assertTrue(actual);
    }

    @Test
    public void testInvalidBestPrizeOfdProfilePlayer() throws CloneNotSupportedException {
        ProfilePlayer testProfilePlayer = (ProfilePlayer) VALID_PROFILE_PLAYER.clone();
        testProfilePlayer.setBestPrize(null);
        boolean actual = profilePlayerValidator.isValid(testProfilePlayer);
        assertFalse(actual);
    }

    @Test
    public void testInvalidPhotoOfdProfilePlayer() throws CloneNotSupportedException {
        ProfilePlayer testProfilePlayer = (ProfilePlayer) VALID_PROFILE_PLAYER.clone();
        testProfilePlayer.setPhoto("""
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa129symbol
        """);
        boolean actual = profilePlayerValidator.isValid(testProfilePlayer);
        assertFalse(actual);
    }
}
