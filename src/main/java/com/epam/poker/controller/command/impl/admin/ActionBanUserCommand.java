package com.epam.poker.controller.command.impl.admin;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.AdminService;
import com.epam.poker.service.database.impl.AdminServiceImpl;

public class ActionBanUserCommand implements Command {
    private static AdminService adminService = AdminServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String response = adminService.banUserById(requestContext);
        CommandResult commandResult = new CommandResult(true);
        commandResult.setJsonResponse(response);
        return commandResult;
    }
}
