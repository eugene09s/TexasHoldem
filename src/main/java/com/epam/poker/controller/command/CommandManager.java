package com.epam.poker.controller.command;

import com.epam.poker.controller.command.constant.CommandName;
import com.epam.poker.controller.command.impl.game.GoToGamePage;
import com.epam.poker.controller.command.impl.general.*;
import com.epam.poker.controller.command.impl.user.*;

public enum CommandManager {
    GO_TO_SIGN_UP_PAGE(new GoToSignUpPageCommand(), CommandName.SIGN_UP_PAGE),
    GO_TO_LOGIN_PAGE(new GoToLoginPageCommand(), CommandName.LOGIN_PAGE),
    GO_TO_PROFILE_PAGE(new GoToProfilePageCommand(), CommandName.PROFILE_PAGE),
    GO_TO_HOME_PAGE(new GoToHomePageCommand(), CommandName.HOME_PAGE),
    SIGN_UP(new SignUpCommand(), CommandName.SIGN_UP),
    LOGIN(new LoginCommand(), CommandName.LOGIN),
    LOGOUT(new LogoutCommand(), CommandName.LOGOUT),
    GO_TO_GAME_PAGE(new GoToGamePage(), CommandName.PLAY_PAGE),
    LOCALIZATION(new LocalizationCommand(), CommandName.LOCALIZATION),
    GO_TO_CHAT(new GoToChatPageCommand(), CommandName.CHAT_PAGE),

    //AJAX
    CHECK_EXIST_LOGIN(new CheckExistUsername(), CommandName.CHECK_EXIST_LOGIN),
    CHECK_EXIST_EMAIL(new CheckExistUsername(), CommandName.CHECK_EXIST_EMAIL);

    private final Command command;
    private final String commandName;

    CommandManager(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }

    static Command of(String name) {
        for (CommandManager action : CommandManager.values()) {
            if (action.getCommandName().equalsIgnoreCase(name)) {
                return action.command;
            }
        }
        return GoToNotFoundPageCommand.INSTANCE;
    }
}
