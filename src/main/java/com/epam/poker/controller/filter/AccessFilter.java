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
        switch (commandName) {
            case CommandName.SIGN_UP:
            case CommandName.SIGN_UP_PAGE:
            case CommandName.LOGIN:
            case CommandName.LOGIN_PAGE:
                return roleLine.equalsIgnoreCase(UserRole.GUEST.toString());
            case CommandName.BLOCK_USER:
            case CommandName.UNBLOCK_USER:
            case CommandName.USERS:
                return roleLine.equalsIgnoreCase(ADMIN_ROLE);
        }
        return true;
    }

    @Override
    public void destroy() {
    }
}
