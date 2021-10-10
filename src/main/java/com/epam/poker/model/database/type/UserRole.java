package com.epam.poker.model.database.type;

import java.util.HashSet;
import java.util.Set;

import static com.epam.poker.util.constant.CommandName.*;

public enum UserRole {
    ADMIN(CHANGE_PASSWORD),
    USER(HOME_PAGE, LOCALIZATION, PROFILE_PAGE, LOGOUT, UPLOAD_PHOTO, PLAY_PAGE, CHAT_PAGE, LOBBY_DATA, TABLE_DATA,
            ACCOUNT_SETTINGS_PAGE, CHANGE_PASSWORD, CHANGE_GENERAL_INFO),
    GUEST(HOME_PAGE, LOCALIZATION, SIGN_UP, SIGN_UP_PAGE, LOGIN, LOGIN_PAGE, CHECK_EXIST_LOGIN, CHECK_EXIST_EMAIL, PLAY_PAGE),
    MANAGER;
    private Set<String> commandsName = new HashSet<>();

    UserRole(String... commandsName) {
        this.commandsName.addAll(Set.of(commandsName));
    }

    public boolean isExistCommandName(String command) {
        return this.commandsName.contains(command);
    }
}
