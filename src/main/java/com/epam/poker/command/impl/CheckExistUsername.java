package com.epam.poker.command.impl;

import com.epam.poker.command.Command;
import com.epam.poker.command.CommandResult;
import com.epam.poker.command.constant.Parameter;
import com.epam.poker.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.logic.service.user.SignUpService;
import com.epam.poker.logic.service.user.UserService;

import java.util.HashMap;

public class CheckExistUsername implements Command {
    private final SignUpService service;

    public CheckExistUsername(SignUpService userService) {
        this.service = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
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
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("isExistUsername", String.valueOf(isExistUsername));
        CommandResult commandResult = new CommandResult("/",true);
        commandResult.addResponseAjax(hashMap);
        return commandResult;
    }
}
