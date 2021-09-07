package com.epam.poker.controller.filter;

import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.model.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.logic.service.user.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class BlockFilter implements Filter {
    private static final UserServiceImpl USER_SERVICE = new UserServiceImpl(new DaoSaveTransactionFactory());

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
                if (USER_SERVICE.isBlockedById(userId)) {
                    session.invalidate();
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
