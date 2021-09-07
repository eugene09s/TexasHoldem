package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.constant.Parameter;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.logic.service.user.SignUpService;

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
        requestContext.addAttribute("isExistUsername", isExistUsername);
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("isExistUsername", String.valueOf(isExistUsername));
        CommandResult commandResult = new CommandResult("/",true);
//        commandResult.addResponseAjax(hashMap);
        return CommandResult.redirect("#");
    }
}
