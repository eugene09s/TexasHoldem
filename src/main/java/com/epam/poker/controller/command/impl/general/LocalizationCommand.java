package com.epam.poker.controller.command.impl.general;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import com.epam.poker.util.constant.PagePath;
import com.epam.poker.util.constant.Parameter;

public class LocalizationCommand implements Command {
    private static final String EN = "en";
    private static final String RU = "ru";
    private static final String EN_LOCALE = "en_US";
    private static final String RU_LOCALE = "ru_RU";
    private static final String PARAMETER_SPLITERATOR = "&";
    private static final String LOGIN_PAGE = "poker?command=" + CommandName.LOGIN_PAGE;
    private static final String SIGN_UP_PAGE = "poker?command=" + CommandName.SIGN_UP_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws InvalidParametersException {
        String language = ParameterTaker.takeString(Parameter.LANGUAGE, requestContext);
        String locale = getLocaleByLanguage(language);
        requestContext.addSession(Attribute.LANGUAGE, locale);
        String page = requestContext.getHeader();
        if (page != null) {
            String prevCommand = extractCommand(page);
            if (CommandName.LOGIN.equals(prevCommand) || CommandName.SIGN_UP.equals(prevCommand)) {
                page = changeCommandToCommandPage(prevCommand);
            }
        }
        return CommandResult.redirect(page);
    }

    private String getLocaleByLanguage(String language) {
        return switch (language) {
            case RU -> RU_LOCALE;
            //case EN -> EN_LOCALE;
            default -> EN_LOCALE;
        };
    }

    private String changeCommandToCommandPage(String prevCommand) {
        switch (prevCommand) {
            case CommandName.LOGIN:
                return LOGIN_PAGE;
            case CommandName.SIGN_UP:
                return SIGN_UP_PAGE;
            default:
                return PagePath.HOME;
        }
    }

    private String extractCommand(String url) {
        int commandIndex = url.indexOf(Parameter.COMMAND) + Parameter.COMMAND.length() + 1;
        int lastCommandIndex = url.indexOf(PARAMETER_SPLITERATOR);
        if (lastCommandIndex == -1) {
            return url.substring(commandIndex);
        } else {
            return url.substring(commandIndex, lastCommandIndex);
        }
    }
}