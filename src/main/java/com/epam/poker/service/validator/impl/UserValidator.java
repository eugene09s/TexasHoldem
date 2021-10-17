package com.epam.poker.service.validator.impl;

import com.epam.poker.service.validator.Validator;
import com.epam.poker.model.database.User;
import com.epam.poker.util.constant.Parameter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UserValidator implements Validator<User> {
    private static final String NAME_PATTERN = "[A-zА-яЁё]+";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern COMPILED_PATTERN_NAME = Pattern.compile(NAME_PATTERN);
    private static final Pattern COMPILED_PATTERN_EMAIL = Pattern.compile(EMAIL_PATTERN);
    private static final int MIN_FIELD_USER_LENGTH = 2;
    private static final int MAX_FIELD_USER_LENGTH = 32;
    private static final int MIN_LOGIN_LENGTH = 8;
    private static final int MIN_PHONE_LENGTH = 12;
    private static final int MAX_PHONE_NUMBER_LENGTH = 18;
    private static final int MAX_EMAIL_LENGTH = 64;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_BIO_LENGTH = 10;
    private static final int MAX_BIO_LENGTH = 512;
    private static final int LENGTH_HASH_PASSWORD = 64;
    private static final List<String> INJECTION_SYMBOLS = List.of("$", "{", "}", "<", ">");
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
        if (!isValidGeneralInfo(user)) {
            return false;
        }
        String login = user.getLogin();
        String password = user.getPassword();
        if (login == null || login.length() > MAX_FIELD_USER_LENGTH
                || login.length() < MIN_LOGIN_LENGTH || !isValidOfInjectionAtеack(login)) {
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

    public boolean isValidPassword(String line) {
        if (line == null || line.length() > MAX_PASSWORD_LENGTH
                || line.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        return true;
    }

    public boolean isValidGeneralInfo(User user) {
        if (user.getFirstName() == null
                || user.getFirstName().length() > MAX_FIELD_USER_LENGTH
                || user.getFirstName().length() < MIN_FIELD_USER_LENGTH
                || !isValidName(user.getFirstName())
                || !isValidOfInjectionAtеack(user.getFirstName())) {
            return false;
        }
        if (user.getLastName() == null
                || user.getLastName().length() > MAX_FIELD_USER_LENGTH
                || user.getLastName().length() < MIN_FIELD_USER_LENGTH
                || !isValidName(user.getLastName())
                || !isValidOfInjectionAtеack(user.getLastName())) {
            return false;
        }
        String phone = String.valueOf(user.getPhoneNumber());
        if (phone.length() > MAX_PHONE_NUMBER_LENGTH || phone.length() < MIN_PHONE_LENGTH) {
            return false;
        }
        return true;
    }

    public boolean isValidBio(String bio) {
        if (bio == null || bio.length() < MIN_BIO_LENGTH
                || bio.length() > MAX_BIO_LENGTH) {
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        Matcher matcher = COMPILED_PATTERN_NAME.matcher(name);
        return matcher.matches();
    }

    private boolean isValidOfInjectionAtеack(String line) {
        for (String injectSymbol : INJECTION_SYMBOLS) {
            if (line.contains(injectSymbol)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = COMPILED_PATTERN_EMAIL.matcher(email);
        return matcher.matches();
    }
}
