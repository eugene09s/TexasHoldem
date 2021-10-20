package com.epam.poker.service.validator;

import com.epam.poker.model.database.User;
import com.epam.poker.service.validator.impl.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {
    private static final UserValidator userValidator = UserValidator.getInstance();
    private static final String VALID_HASH_PASSWORD =
            "b1a932b71b802cc5196d60dc8967bac258d08f8f9dcd5910d1c4b9dd49a1fb7f";
    private static final User VALID_USER = User.builder()
            .setFirstName("Eugene")
            .setLastName("Burdin")
            .setPhoneNumber(322222222222L)
            .setLogin("eugene.burdin")
            .setPassword(VALID_HASH_PASSWORD)
            .setEmail("eugene2003@gmail.com")
            .createUser();

    @Test
    public void testValidUser() {
        boolean actual = userValidator.isValid(VALID_USER);
        assertTrue(actual);
    }

    @Test
    public void testInvalidLoginOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setLogin("qwertyu");
        boolean actual = userValidator.isValid(testUser);
        assertFalse(actual);
    }

    @Test
    public void testInvalidFirstNameOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setFirstName("A");
        boolean actual = userValidator.isValid(testUser);
        assertFalse(actual);
    }

    @Test
    public void testInvalidLastNameOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setLastName("B");
        boolean actual = userValidator.isValid(testUser);
        assertFalse(actual);
    }

    @Test
    public void testInvalidPhoneOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setPhoneNumber(12345678910L);
        boolean actual = userValidator.isValid(testUser);
        assertFalse(actual);
    }

    @Test
    public void testInvalidHashPasswordOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setPassword("adaaa");
        boolean actual = userValidator.isValid(testUser);
        assertFalse(actual);
    }

    @Test
    public void testInvalidEmailOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setEmail("dfh@");
        boolean actual = userValidator.isValid(testUser);
        assertFalse(actual);
    }

    @Test
    public void testInvalidBioOfUser() throws CloneNotSupportedException {
        String testBio = "sfs";
        boolean actual = userValidator.isValidBio(testBio);
        assertFalse(actual);
    }

    @Test
    public void testValidLoginOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setLogin("qwerjlvyvui");
        boolean actual = userValidator.isValid(testUser);
        assertTrue(actual);
    }

    @Test
    public void testValidFirstNameOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setFirstName("Tanya");
        boolean actual = userValidator.isValid(testUser);
        assertTrue(actual);
    }

    @Test
    public void testValidLastNameOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setLastName("Valadi");
        boolean actual = userValidator.isValid(testUser);
        assertTrue(actual);
    }

    @Test
    public void testValidPhoneOfUser() throws CloneNotSupportedException {
        User testUser = (User) VALID_USER.clone();
        testUser.setPhoneNumber(123456789112L);
        boolean actual = userValidator.isValid(testUser);
        assertTrue(actual);
    }

    @Test
    public void testValidPasswordOfUser() throws CloneNotSupportedException {
        String password = "12345678";
        boolean actual = userValidator.isValidPassword(password);
        assertTrue(actual);
    }

    @Test
    public void testInvalidPasswordOfUser() throws CloneNotSupportedException {
        String password = "1234567";
        boolean actual = userValidator.isValidPassword(password);
        assertFalse(actual);
    }

    @Test
    public void testValidBioOfUser() throws CloneNotSupportedException {
        String testBio = "sffghjjjjjjjjjjjjjjjnjjs";
        boolean actual = userValidator.isValidBio(testBio);
        assertTrue(actual);
    }
}
