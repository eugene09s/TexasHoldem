package com.epam.poker.command.impl;

import com.epam.poker.command.Command;
import com.epam.poker.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;

public class LoginCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        return null;
    }
}
