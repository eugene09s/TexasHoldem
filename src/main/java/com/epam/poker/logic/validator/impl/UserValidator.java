package com.epam.poker.logic.validator.impl;

import com.epam.poker.logic.validator.Validator;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final int MAX_FIELD_USER_LENGTH = 32;
    private static final int MIN_FIELD_USER_LENGTH = 2;
    private static final int MIN_LOGIN_AND_PASSWORD_USER_LENGTH = 8;
    private static final int MAX_EMAIL_LENGTH = 64;
    private static final int MAX_PHONE_NUMBER_LENGTH = 18;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern COMPILED_PATTERN_EMAIL = Pattern.compile(EMAIL_PATTERN);
    private static final String NAME_PATTERN = "[A-zА-яЁё]+";
    private static final Pattern COMPILED_PATTERN_NAME = Pattern.compile(NAME_PATTERN);

    @Override
    public boolean isValid(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        UserRole userRole = user.getUserRole();
        String phoneNumber = String.valueOf(user.getPhoneNumber());
        if (login == null || password == null || firstName == null
                || lastName == null || email == null || userRole == null) {
            return false;
        }
        if (phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) {
            return false;
        }
        if (login.length() > MAX_FIELD_USER_LENGTH
                || login.length() < MIN_LOGIN_AND_PASSWORD_USER_LENGTH) {
            return false;
        }
        if (password.length() > MAX_FIELD_USER_LENGTH
                || password.length() < MIN_LOGIN_AND_PASSWORD_USER_LENGTH) {
            return false;
        }
        if (firstName.length() > MAX_FIELD_USER_LENGTH
                || firstName.length() < MIN_FIELD_USER_LENGTH|| !isValidName(firstName)) {
            return false;
        }
        if (lastName.length() > MAX_FIELD_USER_LENGTH
                ||lastName.length() < MIN_FIELD_USER_LENGTH || !isValidName(lastName)) {
            return false;
        }
        if (email.isEmpty() || email.length() > MAX_EMAIL_LENGTH
                || !isValidEmail(email)) {
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = COMPILED_PATTERN_EMAIL.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        Matcher matcher = COMPILED_PATTERN_NAME.matcher(name);
        return matcher.matches();
    }
}
