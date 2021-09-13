package com.epam.poker.controller.filter;


import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.model.entity.type.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AccessFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String WARN_MESSAGE = "Permission denied. Role: ";

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
        UserRole userRole = (UserRole) session.getAttribute(Attribute.ROLE);
        String roleLine;
        if (userRole != null) {
            roleLine = UserRole.USER.toString();
        } else {
            roleLine = UserRole.GUEST.toString();
        }
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

    @Override
    public void destroy() {
    }
}
