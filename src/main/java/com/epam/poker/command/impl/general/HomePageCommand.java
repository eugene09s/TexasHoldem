package com.epam.poker.command.impl.general;

import com.epam.poker.command.Command;
import com.epam.poker.command.CommandResult;
import com.epam.poker.command.constant.Page;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;

public class HomePageCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        return CommandResult.forward(Page.HOME);
    }
}
