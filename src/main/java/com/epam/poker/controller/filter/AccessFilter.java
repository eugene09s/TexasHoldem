package com.epam.poker.controller.filter;


import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.model.entity.type.UserRole;
import com.epam.poker.util.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AccessFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String WARN_MESSAGE = "Permission denied. Role: ";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
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
//        HttpSession session = httpServletRequest.getSession();
//        UserRole userRole = (UserRole) session.getAttribute(Attribute.ROLE);
        Cookie[] cookies = httpServletRequest.getCookies();
        Optional<Cookie> cookieToken = getTokenFromCookies(cookies);
        UserRole userRole;
        if (cookieToken.isPresent()) {
            String token = cookieToken.get().getValue();
            LOGGER.info("Token: " + token);
            JwtProvider jwtProvider = JwtProvider.getInstance();
            if (jwtProvider.validateToken(token)) {
                Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
                userRole = UserRole.valueOf(claimsJws.getBody().get(Attribute.ROLE).toString());
            } else {
                LOGGER.info("Token not valid!");
                userRole = UserRole.GUEST;
            }
        } else {
            LOGGER.info("Token not found or dead!");
            userRole = UserRole.GUEST;
        }
        String roleLine = userRole.toString();
        boolean isAccessAllowed = isAccessAllowed(commandName, roleLine);
        if (isAccessAllowed) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
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

    public boolean checkLifeTimeToken(Cookie cookie) {
        LOGGER.info("Cookie: " + cookie.getMaxAge());
        LOGGER.info("Now: " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        return cookie.getMaxAge() > TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    @Override
    public void destroy() {
    }
}
