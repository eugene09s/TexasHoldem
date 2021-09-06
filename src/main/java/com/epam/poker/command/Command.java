package com.epam.poker.command;

import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;

/**
*   Interface for processing ewquest from web browser.
 */
public interface Command {
    /**
     * Executes request using parameters of RequestContext object and returns
     * CommandResult object with necessary transaction instructions
     *
     * @param requestContext an object which contains all request parameters
     *                       and attributes and session attributes.
     *
     * @return a command result with transaction instructions.
     *
     * @throws ServiceException if logical errors occurs and also
     *                          it's a wrapper for lower errors.
     *
     * @throws InvalidParametersException if there are errors in parameters of request.
     */
    CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException;
}