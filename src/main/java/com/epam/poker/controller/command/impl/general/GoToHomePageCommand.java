package com.epam.poker.controller.command.impl.general;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.PagePath;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;

public class GoToHomePageCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException, DaoException {
        return CommandResult.forward(PagePath.HOME);
    }
}
