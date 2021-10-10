package com.epam.poker.controller.command.util;

import com.epam.poker.util.constant.Parameter;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;

import java.math.BigDecimal;

public class ParameterTaker {

    public static String takeString(String parameterName, RequestContext requestContext) {
        String paramValue = requestContext.getRequestParameter(parameterName);
        return paramValue;
    }

    public static BigDecimal takeNumber(String parameterName, RequestContext requestContext)
            throws InvalidParametersException {
        String numberStr = requestContext.getRequestParameter(parameterName);
        if (numberStr == null) {
            throw new InvalidParametersException("Invalid " + parameterName + " parameter in request.");
        }
        try {
            return new BigDecimal(numberStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid " + parameterName + " parameter in request.");
        }
    }

    public static long takePhoneNumber(String parameterName, RequestContext requestContext)
            throws InvalidParametersException {
        String numberStr = requestContext.getRequestParameter(parameterName);
        if (numberStr == null) {
            throw new InvalidParametersException("Invalid " + parameterName + " parameter in request.");
        }
        try {
            return Long.parseLong(numberStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid " + parameterName + " parameter in request.");
        }
    }

    public static long takeId(RequestContext requestContext)
            throws InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid id parameter in request.");
        }
    }
}
