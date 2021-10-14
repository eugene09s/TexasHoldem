package com.epam.poker.service.database.impl;

import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;
import com.epam.poker.service.database.AdminService;
import com.epam.poker.service.database.UserService;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static AdminService instance = new AdminServiceImpl();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static UserService userService = UserServiceImpl.getInstance();

    public static AdminService getInstance() {
        if (instance == null) {
            instance = new AdminServiceImpl();
        }
        return instance;
    }

    @Override
    public String banUserById(RequestContext requestContext) throws ServiceException {
        long userId = -1;
        String response = null;
        ObjectNode objectNode = mapper.createObjectNode();
        try {
            userId = ParameterTaker.takeId(requestContext);
        } catch (InvalidParametersException e) {
            LOGGER.error("Action ban user, id not found. " + e);
            objectNode.put(Attribute.SUCCESS, false);
            response = String.valueOf(objectNode);
        }
        if (response == null) {
            if (!userService.blockById(userId)) {
                objectNode.put(Attribute.SUCCESS, false);
                LOGGER.warn("User id=" + userId + " not found.");
                throw new ServiceException("User id=" + userId + " not found.");
            } else {
                objectNode.put(Attribute.SUCCESS, true);
                objectNode.put(Attribute.MESSAGE, String.valueOf(UserStatus.BANNED));
            }
            response = String.valueOf(objectNode);
        }
        return response;
    }

    @Override
    public String unbanUserById(RequestContext requestContext) throws ServiceException {
        long userId = -1;
        String response = null;
        ObjectNode objectNode = mapper.createObjectNode();
        try {
            userId = ParameterTaker.takeId(requestContext);
        } catch (InvalidParametersException e) {
            LOGGER.error("Action ban user, id not found. " + e);
            objectNode.put(Attribute.SUCCESS, false);
            response = String.valueOf(objectNode);
        }
        if (response == null) {
            if (!userService.unblockById(userId)) {
                objectNode.put(Attribute.SUCCESS, false);
                LOGGER.warn("User id=" + userId + " not found.");
                throw new ServiceException("User id=" + userId + " not found.");
            } else {
                objectNode.put(Attribute.SUCCESS, true);
                objectNode.put(Attribute.MESSAGE, String.valueOf(UserStatus.ACTIVE));
            }
            response = String.valueOf(objectNode);
        }
        return response;
    }
}
