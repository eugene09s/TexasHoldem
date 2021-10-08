package com.epam.poker.controller.filter;

import com.epam.poker.util.constant.Attribute;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.jwt.JwtProvider;
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

public class BlockFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserService userService = UserServiceImpl.getInstance();
    private static JwtProvider jwtProvider = JwtProvider.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Cookie[] cookies = httpServletRequest.getCookies();
        Optional<Cookie> cookieToken = getTokenFromCookies(cookies);
        if (cookieToken.isPresent()) {
            String token = cookieToken.get().getValue();
            if (jwtProvider.validateToken(token)) {
                Jws<Claims> claimsJws = jwtProvider.getClaimsFromToken(token);
                long userId = Long.parseLong((String) claimsJws.getBody().get(Attribute.USER_ID));
                try {
                    if (userService.isBlockedById(userId)) {
                        session.setAttribute(Attribute.INVALIDATE_ATTRIBUTE, true);
                        HttpServletResponse response = (HttpServletResponse) servletResponse;
                        Cookie deleteBlackToken = new Cookie(Attribute.ACCESS_TOKEN, null);
                        deleteBlackToken.setMaxAge(0);
                        response.addCookie(deleteBlackToken);
                    }
                } catch (ServiceException e) {
                    LOGGER.warn("Check user blocking by id: " + e);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
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
