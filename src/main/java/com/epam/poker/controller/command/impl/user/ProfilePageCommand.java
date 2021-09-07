package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.Page;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.logic.service.user.ProfilePlayerService;
import com.epam.poker.model.logic.service.user.UserService;
import com.epam.poker.model.entity.ProfilePlayer;
import com.epam.poker.model.entity.User;

public class ProfilePageCommand implements Command {
    private final ProfilePlayerService profilePlayerService;
    private final UserService userService;

    public ProfilePageCommand(ProfilePlayerService profilePlayerService, UserService userService) {
        this.profilePlayerService = profilePlayerService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        long id;
        id = ParameterTaker.takeId(requestContext);
        ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(id);
        User user = userService.findUserById(id);
        requestContext.addAttribute(Attribute.USER, user);
        requestContext.addAttribute(Attribute.PROFILE_PLAYER, profilePlayer);
        return CommandResult.forward(Page.PROFILE);
    }
}
