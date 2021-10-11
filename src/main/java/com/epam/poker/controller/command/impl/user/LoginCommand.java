package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.util.LineHasher;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import com.epam.poker.util.constant.PagePath;
import com.epam.poker.util.constant.Parameter;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.service.database.UserService;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.jwt.ConfigReaderJwt;
import com.epam.poker.util.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginCommand implements Command {
    private static final String PROFILE_PAGE_COMMAND = "poker?command=" + CommandName.PROFILE_PAGE + "&id=";
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String BANNED_USER_KEY = "banned";
    private static final long LIFE_TIME_COOKIE = ConfigReaderJwt.getAccessTokenLifeTime();
    private static UserService service = UserServiceImpl.getInstance();
    private static ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static final int TIMEZONE_GMT_PLUS_THREE = 60*60*3;
    private static JwtProvider jwtProvider = JwtProvider.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String login = ParameterTaker.takeString(Parameter.LOGIN, requestContext);
        String pass = ParameterTaker.takeString(Parameter.PASSWORD, requestContext);
        LineHasher lineHasher = new LineHasher();
        String hashPass = lineHasher.hashingLine(pass);
        boolean isUserExist = service.isUserExistByLoginPassword(login, hashPass);
        if (isUserExist) {
            User user = service.findUserByLogin(login);
            if (user.getUserStatus() != UserStatus.BANNED) {
                long id = user.getUserId();
                UserRole role = user.getUserRole();
                requestContext.addSession(Attribute.USER_ID, id);
                requestContext.addSession(Attribute.ROLE, role);
                requestContext.addSession(Attribute.LOGIN, user.getLogin());
                ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(id);
                requestContext.addSession(Attribute.PHOTO, profilePlayer.getPhoto());
                //create jwt token
                Map<String, String> claims = new HashMap<>();
                claims.put(Attribute.USER_ID, String.valueOf(id));
                claims.put(Attribute.ROLE, String.valueOf(role));
                claims.put(Attribute.LOGIN, user.getLogin());
                String token = jwtProvider.generateToken(claims);
                Cookie cookie = new Cookie(Attribute.ACCESS_TOKEN, token);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(TIMEZONE_GMT_PLUS_THREE +
                        (int) (TimeUnit.MINUTES.toSeconds(LIFE_TIME_COOKIE)));
                requestContext.addCookie(cookie);
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
