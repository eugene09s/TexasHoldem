package com.epam.poker.controller;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.CommandName;
import com.epam.poker.controller.command.constant.PagePath;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.controller.request.RequestContextCreator;
import com.epam.poker.controller.request.RequestFiller;
import com.epam.poker.exception.DaoException;
import com.epam.poker.model.pool.ConnectionPool;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/poker"}, name = "mainServlet")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
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
        RequestContextCreator requestContextCreator = new RequestContextCreator();
        CommandResult commandResult;
        String commandParam = req.getParameter(Parameter.COMMAND);
        Command command = Command.of(commandParam);
        try {
            RequestContext requestContext = requestContextCreator.create(req);
            commandResult = command.execute(requestContext);
            RequestFiller requestFiller = new RequestFiller();
            requestFiller.fillData(req, requestContext);
            dispatch(commandResult, req, resp);
        } catch (Exception e) {
            LOGGER.error(e);
            handleException(req, resp, e.getMessage());
        }
    }

    private void dispatch(CommandResult commandResult,
                          HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (commandResult.isEmptyResponseAjax()) {//TODO new servlet for AJAX
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
            HashMap<String, String> hashMap = commandResult.getResponseAjax();
            response.setContentType("text/plain");
            response.getWriter().write(hashMap.get(Attribute.CHECK_USERNAME_EXIST));
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
