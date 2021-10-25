package com.epam.poker.controller.filter;

import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*", "/game/index.html"})
public class SecurityFilter implements Filter {
    private static final String PERMISSION_DENIED = "Permission denied";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
        httpServletRequest.setAttribute(Attribute.ERROR_MESSAGE, PERMISSION_DENIED);
    }

    @Override
    public void destroy() {
    }
}
