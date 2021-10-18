package com.epam.poker.controller.command.impl.general;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.util.constant.PagePath;

public class GoToHomePageCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        return CommandResult.forward(PagePath.HOME);
    }
}
