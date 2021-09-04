package com.epam.poker.command;

import com.epam.poker.command.constant.CommandName;
import com.epam.poker.command.impl.*;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.logic.service.user.SignUpServiceImpl;
import com.epam.poker.logic.service.user.UserServiceImpl;
import com.epam.poker.logic.validator.impl.UserValidator;

public class CommandFactory {

    public static Command createCommand(String commandParam) {
        if (commandParam == null) {
            throw new IllegalArgumentException("There is no command to do.");
        }
        return switch (commandParam) {
            case CommandName.SIGN_UP -> new SignUpCommand(new SignUpServiceImpl(new DaoSaveTransactionFactory(), new UserValidator()));
            case CommandName.LOGIN ->  new LoginCommand(new UserServiceImpl(new DaoSaveTransactionFactory()));
            case CommandName.LOGOUT ->  new LogoutCommand();
            case CommandName.HOME_PAGE -> new HomePageCommand();
            case CommandName.SIGN_UP_PAGE, CommandName.LOGIN_PAGE -> new ForwardPageCommand(commandParam);
            case CommandName.CHECK_EXIST_LOGIN,
                    CommandName.CHECK_EXIST_EMAIL -> new CheckExistUsername(new SignUpServiceImpl(new DaoSaveTransactionFactory(), new UserValidator()));

            default -> throw new IllegalArgumentException("Unknown command: " + commandParam);
        };
    }
}
