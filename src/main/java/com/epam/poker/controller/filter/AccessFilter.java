package com.epam.poker.controller.filter;

import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.CommandName;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.model.entity.type.UserRole;
import com.epam.poker.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class AccessFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String WARN_MESSAGE = "Permission denied. Role: ";
    private static final String PERMISSION_DENIED = "Permission denied";
    private static final String HOME_PAGE_COMMAND = "poker?command=" + CommandName.HOME_PAGE;
    private static JwtProvider jwtProvider = JwtProvider.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String commandName = servletRequest.getParameter(Parameter.COMMAND);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        Cookie[] cookies = httpServletRequest.getCookies();
        Optional<Cookie> cookieToken = getTokenFromCookies(cookies);
        UserRole userRole;
        if (cookieToken.isPresent()) {
            String token = cookieToken.get().getValue();
            if (jwtProvider.validateToken(token)) {
                Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
                userRole = UserRole.valueOf(claimsJws.getBody().get(Attribute.ROLE).toString());
                UserRole userRoleSession = (UserRole) session.getAttribute(Attribute.ROLE);
                if (userRoleSession == null || userRoleSession != userRole) {
                    session.setAttribute(Attribute.USER_ID, claimsJws.getBody().get(Attribute.USER_ID));
                    session.setAttribute(Attribute.LOGIN, claimsJws.getBody().get(Attribute.LOGIN));
                    session.setAttribute(Attribute.PHOTO, claimsJws.getBody().get(Attribute.PHOTO));
                }
            } else {
                LOGGER.info("Token not valid!");
                userRole = UserRole.GUEST;
                session.setAttribute(Attribute.INVALIDATE_ATTRIBUTE, true);
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                Cookie deleteBlackToken = new Cookie(Attribute.ACCESS_TOKEN, null);
                deleteBlackToken.setMaxAge(0);
                response.addCookie(deleteBlackToken);
            }
        } else {
            LOGGER.info("Token not found or dead!");
            userRole = UserRole.GUEST;
        }

        session.setAttribute(Attribute.ROLE, userRole);
        String roleLine = userRole.toString();
        boolean isAccessAllowed = isAccessAllowed(commandName, roleLine);
        if (isAccessAllowed) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
            httpServletRequest.setAttribute(Attribute.ERROR_MESSAGE, PERMISSION_DENIED);
        }
    }

    private boolean isAccessAllowed(String commandName, String roleLine) {
        if (commandName == null) {
            return true;
        }
        try {
            return UserRole.valueOf(roleLine).isExistCommandName(commandName);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(WARN_MESSAGE + roleLine);
            return false;
        }
    }

    private Optional<Cookie> getTokenFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(Attribute.ACCESS_TOKEN))
                .findFirst();
    }

    @Override
    public void destroy() {
    }
}
