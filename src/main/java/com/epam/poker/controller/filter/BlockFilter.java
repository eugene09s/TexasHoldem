package com.epam.poker.controller.filter;

import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.service.user.UserService;
import com.epam.poker.model.service.user.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BlockFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserService userService = UserServiceImpl.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        Long userId = (Long) session.getAttribute(Attribute.USER_ID);
        if (userId != null) {
            try {
                try {
                    if (userService.isBlockedById(userId)) {
                        session.invalidate();
                    }
                } catch (DaoException e) {
                    LOGGER.error("Block filter error: " + e);
                }
            } catch (ServiceException e) {
                session.invalidate();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
