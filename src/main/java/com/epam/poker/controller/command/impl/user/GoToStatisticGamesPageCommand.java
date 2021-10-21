package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.StatisticGameCommandHelper;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.util.constant.PagePath;

public class GoToStatisticGamesPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        StatisticGameCommandHelper statisticGameCommandHelper = new StatisticGameCommandHelper();
        statisticGameCommandHelper.processCommandWithPagination(requestContext);
        return CommandResult.forward(PagePath.STATISTIC_GAMES);
    }
}
