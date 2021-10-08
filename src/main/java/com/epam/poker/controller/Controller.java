package com.epam.poker.controller;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import com.epam.poker.util.constant.PagePath;
import com.epam.poker.util.constant.Parameter;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.pool.ConnectionPool;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(urlPatterns = {"/poker"}, name = "mainServlet")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson gson = new Gson();
    private static final String HOME_PAGE_COMMAND = "poker?command=" + CommandName.HOME_PAGE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CommandResult commandResult;
        String commandParam = req.getParameter(Parameter.COMMAND);
        Command command = Command.of(commandParam);
        try {
            RequestContext requestContext = new RequestContext(req);
            commandResult = command.execute(requestContext);
            requestContext.fillData(req, resp);
            dispatch(commandResult, req, resp);
        } catch (Exception e) {
            LOGGER.error(e);
            handleException(req, resp, e.getMessage());
        }
    }

    private void dispatch(CommandResult commandResult,
                          HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        if (!commandResult.isTypeResponseJson()) {//TODO Perhaps new servlet for send json
            String page = commandResult.getPage();
            if (page == null) {
                response.sendRedirect(HOME_PAGE_COMMAND);
            } else {
                if (commandResult.isRedirect()) {
                    response.sendRedirect(page);
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                    dispatcher.forward(request, response);
                }
            }
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(commandResult.getJsonResponse());
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws IOException {
        req.setAttribute(Attribute.ERROR_MESSAGE, errorMessage);
        RequestDispatcher dispatcher = req.getRequestDispatcher(PagePath.ERROR);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            LOGGER.error(e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.closeAll();
        } catch (DaoException e) {
            LOGGER.error(e);
        }
        super.destroy();
    }
}
