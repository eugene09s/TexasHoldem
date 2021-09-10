package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.CommandName;
import com.epam.poker.controller.command.constant.PagePath;
import com.epam.poker.controller.command.constant.Parameter;
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
import com.epam.poker.model.entity.type.UserRole;
import com.epam.poker.model.entity.type.UserStatus;
import com.epam.poker.model.service.user.UserServiceImpl;

public class LoginCommand implements Command {
    private static final String PROFILE_PAGE_COMMAND = "poker?command=" + CommandName.PROFILE_PAGE + "&id=";
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String BANNED_USER_KEY = "banned";
    private static UserService service = UserServiceImpl.getInstance();
    private static ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException, DaoException {
        String login = ParameterTaker.takeString(Parameter.LOGIN, requestContext);
        String password = ParameterTaker.takeString(Parameter.PASSWORD, requestContext);
        boolean isUserExist = service.isUserExistByLoginPassword(login, password);
        if (isUserExist) {
            User user = service.findUserByLogin(login);
            if (user.getUserStatus() != UserStatus.BANNED) {
                long id = user.getUserId();
                UserRole role = user.getUserRole();
                requestContext.addSession(Attribute.USER_ID, id);
                requestContext.addSession(Attribute.ROLE, role);
                ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(id);
                requestContext.addSession(Attribute.PHOTO, profilePlayer.getPhoto());
                return CommandResult.redirect(PROFILE_PAGE_COMMAND + id);
            }
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, BANNED_USER_KEY);
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, INCORRECT_DATA_KEY);
        }
        requestContext.addAttribute(Attribute.SAVED_LOGIN, login);
        return CommandResult.forward(PagePath.LOGIN);
    }
}
