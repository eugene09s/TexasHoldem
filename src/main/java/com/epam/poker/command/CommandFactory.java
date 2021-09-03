package com.epam.poker.command;

import com.epam.poker.command.constant.CommandName;
import com.epam.poker.command.impl.LoginCommand;
import com.epam.poker.command.impl.LogoutCommand;
import com.epam.poker.command.impl.SignUpCommand;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.logic.service.user.SignUpServiceImpl;
import com.epam.poker.logic.validator.impl.UserValidator;

public class CommandFactory {

    public static Command createCommand(String commandParam) {
        if (commandParam == null) {
            throw new IllegalArgumentException("There is no command to do.");
        }
        return switch (commandParam) {
            case CommandName.SIGN_UP -> new SignUpCommand(new SignUpServiceImpl(new DaoSaveTransactionFactory(), new UserValidator()));
            case CommandName.LOGIN ->  new LoginCommand();
            case CommandName.LOGOUT ->  new LogoutCommand();
            default -> throw new IllegalArgumentException("Unknown command: " + commandParam);
        };
    }
}
