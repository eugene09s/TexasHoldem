package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.PagePath;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.service.user.ProfilePlayerService;
import com.epam.poker.model.service.user.ProfilePlayerServiceImpl;
import com.epam.poker.model.service.user.UserService;
import com.epam.poker.model.entity.ProfilePlayer;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.service.user.UserServiceImpl;

public class GoToProfilePageCommand implements Command {
    private static ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException, DaoException {
        long id;
        id = ParameterTaker.takeId(requestContext);
        ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(id);
        User user = userService.findUserById(id);
        requestContext.addAttribute(Attribute.USER, user);
        requestContext.addAttribute(Attribute.PROFILE_PLAYER, profilePlayer);
        return CommandResult.forward(PagePath.PROFILE);
    }
}
