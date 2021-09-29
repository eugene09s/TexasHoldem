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

import java.util.HashMap;

public class CheckExistUsername implements Command {
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
        requestContext.addAttribute(Attribute.CHECK_USERNAME_EXIST, isExistUsername);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Attribute.CHECK_USERNAME_EXIST, String.valueOf(isExistUsername));
        CommandResult commandResult = new CommandResult("/",true);
        commandResult.addResponseAjax(hashMap);
        return commandResult;
    }
}
