package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.service.user.SignUpService;
import com.epam.poker.model.service.user.impl.SignUpServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class CheckExistUsernameCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static SignUpService service = SignUpServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, DaoException {
        boolean isExistUsername = true;
        String username = ParameterTaker.takeString(Parameter.LOGIN, requestContext);
        if (username == null) {
            username = ParameterTaker.takeString(Parameter.EMAIL, requestContext);
            if (username != null) {
                isExistUsername = service.isUserEmailExist(username);
            }
        } else {
            isExistUsername = service.isUserLoginExist(username);
        }
        ObjectNode response = mapper.createObjectNode();
        response.put(Attribute.CHECK_USERNAME_EXIST, String.valueOf(isExistUsername));
        CommandResult commandResult = new CommandResult(null,false);
        commandResult.setTypeResponseJson(true);
        try {
            commandResult.setJsonResponse(mapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json to line: " + e);
        }
        return commandResult;
    }
}
