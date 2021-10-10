package com.epam.poker.service.validator;

import com.epam.poker.model.database.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifiableDataOfUserValidator {
    private static final int MAX_FIELD_USER_LENGTH = 32;
    private static final int MIN_FIELD_USER_LENGTH = 2;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_PHONE_LENGTH = 12;
    private static final int MAX_PHONE_LENGTH = 18;
    private static final int MIN_BIO_LENGTH = 10;
    private static final int MAX_BIO_LENGTH = 512;
    private static final String NAME_PATTERN = "[A-zА-яЁё]+";
    private static final Pattern COMPILED_PATTERN_NAME = Pattern.compile(NAME_PATTERN);

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
                || !isValidName(user.getFirstName())) {
            return false;
        }
        if (user.getLastName() == null
                || user.getLastName().length() > MAX_FIELD_USER_LENGTH
                ||user.getLastName().length() < MIN_FIELD_USER_LENGTH
                || !isValidName(user.getLastName())) {
            return false;
        }
        String phone = String.valueOf(user.getPhoneNumber());
        if (phone.length() > MAX_PHONE_LENGTH || phone.length() < MIN_PHONE_LENGTH) {
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
}
