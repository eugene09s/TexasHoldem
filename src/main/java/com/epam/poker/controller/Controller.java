package com.epam.poker.controller;

import com.epam.poker.command.Command;
import com.epam.poker.command.CommandFactory;
import com.epam.poker.command.CommandResult;
import com.epam.poker.command.constant.Attribute;
import com.epam.poker.command.constant.CommandName;
import com.epam.poker.command.constant.Page;
import com.epam.poker.command.constant.Parameter;
import com.epam.poker.connection.ConnectionPool;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.controller.request.RequestContextCreator;
import com.epam.poker.controller.request.RequestFiller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/poker", "*.do"}, name = "mainServlet")
@MultipartConfig(location = "P:\\epam\\data", maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 2)
public class Controller extends HttpServlet {
    private static final String HOME_PAGE_COMMAND = "poker?command=" + CommandName.HOME_PAGE;
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestContextCreator requestContextCreator = new RequestContextCreator();
        CommandResult commandResult;
        String commandParam = req.getParameter(Parameter.COMMAND);
        Command command;
        try {
            command = CommandFactory.createCommand(commandParam);
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
        if (commandResult.isEmptyResponseAjax()) {
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
            response.getWriter().write(hashMap.get("isExistUsername"));
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws IOException {
        req.setAttribute(Attribute.ERROR_MESSAGE, errorMessage);
        RequestDispatcher dispatcher = req.getRequestDispatcher(Page.ERROR);
        try {
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            LOGGER.fatal(e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.closeAll();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        super.destroy();
    }
}
