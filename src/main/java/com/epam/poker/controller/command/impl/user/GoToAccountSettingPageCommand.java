package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
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
import com.epam.poker.util.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.Cookie;

import java.util.Optional;

public class GoToAccountSettingPageCommand implements Command {
    private static ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static UserService userService = UserServiceImpl.getInstance();
    private static JwtProvider jwtProvider = JwtProvider.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        Optional<Cookie> cookieToken = jwtProvider.getTokenFromCookies(requestContext.getCookies());
        String token = cookieToken.get().getValue();
        Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
        long userId = Long.parseLong(String.valueOf(claimsJws.getBody().get(Attribute.USER_ID)));
        ProfilePlayer profilePlayer = profilePlayerService.findProfilePlayerById(userId);
        User user = userService.findUserById(userId);
        requestContext.addAttribute(Attribute.USER, user);
        requestContext.addAttribute(Attribute.PROFILE_PLAYER, profilePlayer);
        return CommandResult.forward(PagePath.SETTINGS);
    }
}
