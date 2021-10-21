package com.epam.poker.controller.command.impl.admin;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.UserDtoCommandHelper;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.util.constant.PagePath;

public class GoToAdminPanelPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        UserDtoCommandHelper userDtoCommandHelper = new UserDtoCommandHelper();
        userDtoCommandHelper.processCommandWithPagination(requestContext);
        return CommandResult.forward(PagePath.ADMIN_PANEL);
    }
}
