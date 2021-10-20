package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfilePlayerServiceTest {
    private static final ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static final long TEST_USER_ID = 9;
    private static final  ProfilePlayer TEST_PROFILE_PLAYER = ProfilePlayer
            .builder()
            .setUserId(TEST_USER_ID)
            .setPhoto("")
            .setAward("")
            .setAboutYourself("")
            .setWinMoney(BigDecimal.ZERO)
            .setLostMoney(BigDecimal.ZERO)
            .setBestPrize(BigDecimal.ZERO)
            .createRatingPlayer();

    @Test
    public void testExpectedExceptionFindUserByLogin() {
        Assertions.assertThrows(ServiceException.class, () -> {
            profilePlayerService.findProfilePlayerById(-1);
        });
    }

    @Test
    public void testInvalidUpdatePhotoByUserId() {
        boolean actual = false;
        try {
            actual = profilePlayerService.updatePhotoByUserId(-1, "photo");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(actual);
    }

    @Test
    public void testValidUpdatePhotoByUserId() {
        boolean actual = false;
        try {
            actual = profilePlayerService.updatePhotoByUserId(TEST_USER_ID, "notAva.jpg");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(actual);
    }

    @Test
    public void testFindProfilePlayerById() {
        ProfilePlayer profilePlayer = null;
        boolean actual = false;
        try {
            profilePlayer = profilePlayerService.findProfilePlayerById(TEST_USER_ID);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (profilePlayer != null) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void testUpdateAwardByUserId() {
        ProfilePlayer profilePlayer = null;
        boolean actual = false;
        String award = "you top winner";
        try {
            profilePlayer = profilePlayerService.findProfilePlayerById(TEST_USER_ID);
            profilePlayerService.updateAwardByUserId(TEST_USER_ID, award);
            profilePlayer = profilePlayerService.findProfilePlayerById(TEST_USER_ID);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (profilePlayer != null && award.equals(profilePlayer.getAward())) {
            actual = true;
            try {
                profilePlayerService.updateAwardByUserId(TEST_USER_ID, "Your feature award");
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        assertTrue(actual);
    }
}
