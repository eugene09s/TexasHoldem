package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.PagePath;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.service.database.ProfilePlayerService;
import com.epam.poker.model.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.model.service.database.UserService;
import com.epam.poker.model.entity.database.ProfilePlayer;
import com.epam.poker.model.entity.database.User;
import com.epam.poker.model.service.database.impl.UserServiceImpl;

public class GoToProfilePageCommand implements Command {
    private static ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException, DaoException {
        long id = ParameterTaker.takeId(requestContext);
        ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(id);
        User user = userService.findUserById(id);
        requestContext.addAttribute(Attribute.USER, user);
        requestContext.addAttribute(Attribute.PROFILE_PLAYER, profilePlayer);
        return CommandResult.forward(PagePath.PROFILE);
    }
}
