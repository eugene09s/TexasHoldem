package com.epam.poker.command.impl;

import com.epam.poker.command.Command;
import com.epam.poker.command.CommandResult;
import com.epam.poker.command.constant.Attribute;
import com.epam.poker.command.constant.CommandName;
import com.epam.poker.command.constant.Page;
import com.epam.poker.command.constant.Parameter;
import com.epam.poker.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.logic.service.user.UserService;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;
import com.thoughtworks.qdox.model.expression.ShiftRight;

public class LoginCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "poker?command=" + CommandName.HOME_PAGE;
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String BANNED_USER_KEY = "banned";
    private final UserService service;

    public LoginCommand(UserService userService) {
        this.service = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String login = ParameterTaker.takeString(Parameter.LOGIN, requestContext);
        String password = ParameterTaker.takeString(Parameter.PASSWORD, requestContext);
        boolean isUserExist = service.isUserExistByLoginPassword(login, password);
        if (isUserExist) {
            User user = service.findUserByLogin(login);
            if (!(user.getUserStatus() == UserStatus.BANNED)) {
                long id = user.getUserId();
                requestContext.addSession(Attribute.USER_ID, id);
                UserRole role = user.getUserRole();
                requestContext.addSession(Attribute.ROLE, role);
                return CommandResult.redirect(HOME_PAGE_COMMAND);
            }
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, BANNED_USER_KEY);
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, INCORRECT_DATA_KEY);
        }
        requestContext.addAttribute(Attribute.SAVED_LOGIN, login);
        return CommandResult.forward(Page.LOGIN);
    }
}
