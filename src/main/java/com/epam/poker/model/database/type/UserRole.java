package com.epam.poker.model.database.type;

import java.util.HashSet;
import java.util.Set;

import static com.epam.poker.util.constant.CommandName.*;

public enum UserRole {
    ADMIN(HOME_PAGE, LOCALIZATION, PROFILE_PAGE, LOGOUT, UPLOAD_PHOTO, PLAY_PAGE, CHAT_PAGE, LOBBY_DATA, TABLE_DATA,
            ACCOUNT_SETTINGS_PAGE, CHANGE_PASSWORD, CHANGE_GENERAL_INFO, USERS_PAGE, STATISTIC_GAMES_PAGE, ADMIN_PANEL_PAGE,
            ACTION_BAN_USER, ACTION_UNBAN_USER, ACTION_CHANGE_BALANCE),
    USER(HOME_PAGE, LOCALIZATION, PROFILE_PAGE, LOGOUT, UPLOAD_PHOTO, PLAY_PAGE, CHAT_PAGE, LOBBY_DATA, TABLE_DATA,
            ACCOUNT_SETTINGS_PAGE, CHANGE_PASSWORD, CHANGE_GENERAL_INFO, USERS_PAGE, STATISTIC_GAMES_PAGE),
    GUEST(HOME_PAGE, LOCALIZATION, SIGN_UP, SIGN_UP_PAGE, LOGIN, LOGIN_PAGE, CHECK_EXIST_LOGIN, CHECK_EXIST_EMAIL, PLAY_PAGE);

    private final Set<String> commandsName = new HashSet<>();

    UserRole(String... commandsName) {
        this.commandsName.addAll(Set.of(commandsName));
    }

    public boolean isExistCommandName(String command) {
        return this.commandsName.contains(command);
    }
}
