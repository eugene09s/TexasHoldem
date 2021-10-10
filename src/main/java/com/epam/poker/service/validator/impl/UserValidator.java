package com.epam.poker.service.validator.impl;

import com.epam.poker.service.validator.ModifiableDataOfUserValidator;
import com.epam.poker.service.validator.Validator;
import com.epam.poker.model.database.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final int MAX_FIELD_USER_LENGTH = 32;
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int LENGTH_HASH_PASSWORD = 64;
    private static final int MAX_PHONE_NUMBER_LENGTH = 18;
    private static final int MAX_EMAIL_LENGTH = 64;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern COMPILED_PATTERN_EMAIL = Pattern.compile(EMAIL_PATTERN);
    private static UserValidator instance;

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    @Override
    public boolean isValid(User user) {
        ModifiableDataOfUserValidator generalInfoValidator = new ModifiableDataOfUserValidator();
        if (!generalInfoValidator.isValidGeneralInfo(user)
                || !generalInfoValidator.isValidPassword(user.getPassword())) {
            return false;
        }
        String login = user.getLogin();
        String password = user.getPassword();
        String phoneNumber = String.valueOf(user.getPhoneNumber());
        if (phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) {
            return false;
        }
        if (login == null || login.length() > MAX_FIELD_USER_LENGTH
                || login.length() < MIN_LOGIN_LENGTH) {
            return false;
        }
        if (password == null || password.length() != LENGTH_HASH_PASSWORD) {
            return false;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()
                || user.getEmail().length() > MAX_EMAIL_LENGTH
                || !isValidEmail(user.getEmail())) {
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = COMPILED_PATTERN_EMAIL.matcher(email);
        return matcher.matches();
    }
}
