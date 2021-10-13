package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.AccountInfoChangeService;
import com.epam.poker.service.database.impl.AccountInfoChangeServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import com.epam.poker.util.constant.PagePath;
import com.epam.poker.util.constant.Parameter;
import com.epam.poker.util.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.Cookie;

import java.util.Optional;

public class ChangePasswordCommand implements Command {
    private static final String PROFILE_PAGE_COMMAND = "poker?command=" + CommandName.PROFILE_PAGE + "&id=";
    private static final String INVALID_DATA_KEY = "invalid.pass";
    private static final String VALID_DATA_KEY = "success.pass";
    private static AccountInfoChangeService changeDataOfAccountUser = AccountInfoChangeServiceImpl.getInstance();
    private static JwtProvider jwtProvider = JwtProvider.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        String currentPassword = ParameterTaker.takeString(Parameter.CURRENT_PASSWORD, requestContext);
        String newPassword = ParameterTaker.takeString(Parameter.NEW_PASSWORD, requestContext);
        Optional<Cookie> cookieToken = jwtProvider.getTokenFromCookies(requestContext.getCookies());
        String token = cookieToken.get().getValue();
        Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
        long userId = Long.parseLong(String.valueOf(claimsJws.getBody().get(Attribute.USER_ID)));
        boolean isValidCurrentPassword = changeDataOfAccountUser
                .updatePassword(userId, currentPassword, newPassword);
        if (isValidCurrentPassword) {
            requestContext.addAttribute(Attribute.SUCCES_MESSAGE, VALID_DATA_KEY);
            return CommandResult.redirect(PROFILE_PAGE_COMMAND + userId);
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, INVALID_DATA_KEY);
        }
        return CommandResult.forward(PagePath.SETTINGS);
    }
}
