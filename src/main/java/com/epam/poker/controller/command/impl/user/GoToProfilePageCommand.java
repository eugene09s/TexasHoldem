package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.PagePath;

public class GoToProfilePageCommand implements Command {
    private static final ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        long id = ParameterTaker.takeId(requestContext);
        ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(id);
        User user = userService.findUserById(id);
        requestContext.addAttribute(Attribute.USER, user);
        requestContext.addAttribute(Attribute.PROFILE_PLAYER, profilePlayer);
        return CommandResult.forward(PagePath.PROFILE);
    }
}
