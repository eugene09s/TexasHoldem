package com.epam.poker.controller.command.impl.general;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.CommandName;
import com.epam.poker.controller.command.constant.Page;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;

public class ForwardPageCommand implements Command {
    private final String page;

    public ForwardPageCommand(String page) {
        this.page = page;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        if (page == null) {
            throw new IllegalArgumentException("Invalid page.");
        }
        return switch (page) {
            case CommandName.LOGIN_PAGE -> CommandResult.forward(Page.LOGIN);
            case CommandName.SIGN_UP_PAGE -> CommandResult.forward(Page.SIGN_UP);
            case CommandName.HOME_PAGE -> CommandResult.forward(Page.HOME);
            default -> throw new IllegalArgumentException("Unknown page: " + page);
        };
    }
}
