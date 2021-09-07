package com.epam.poker.controller.filter;


import com.epam.poker.command.constant.Attribute;
import com.epam.poker.command.constant.CommandName;
import com.epam.poker.command.constant.Parameter;
import com.epam.poker.model.enumeration.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AccessFilter implements Filter {
    private static final String GUEST_ROLE = "GUEST";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String MANAGER_ROLE = "MANAGER";

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
            roleLine = USER_ROLE;
        } else {
            roleLine = GUEST_ROLE;
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
        return switch (commandName) {
            case CommandName.SIGN_UP,
                    CommandName.SIGN_UP_PAGE,
                    CommandName.LOGIN,
                    CommandName.LOGIN_PAGE -> roleLine.equalsIgnoreCase(UserRole.GUEST.toString());
            case CommandName.PROFILE_PAGE,
                    CommandName.LOGOUT -> roleLine.equalsIgnoreCase(UserRole.USER.toString());
            case CommandName.BLOCK_USER,
                    CommandName.UNBLOCK_USER,
                    CommandName.USERS -> roleLine.equalsIgnoreCase(ADMIN_ROLE);
            case CommandName.HOME_PAGE -> true;
            default -> false;
        };
    }

    @Override
    public void destroy() {
    }
}
