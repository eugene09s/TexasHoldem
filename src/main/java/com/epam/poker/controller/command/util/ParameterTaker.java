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

//    public static int takePageNumber(RequestContext requestContext)
//            throws InvalidParametersException {
//        String pageStr = requestContext.getRequestParameter(Parameter.PAGE);
//        int page;
//        try {
//            page = Integer.parseInt(pageStr);
//        } catch (NumberFormatException e) {
//            throw new InvalidParametersException("Invalid page number parameter in request.");
//        }
//        if (page < 1) {
//            throw new InvalidParametersException("Not positive page number parameter in request.");
//        }
//        return page;
//    }
//
//    public static MatchTeamNumber extractTeamBet(RequestContext requestContext)
//            throws InvalidParametersException {
//        String teamBetName = requestContext.getRequestParameter(Parameter.BET_ON);
//        if (teamBetName == null) {
//            throw new InvalidParametersException("Invalid team bet parameter in request.");
//        }
//        try {
//            return MatchTeamNumber.valueOf(teamBetName);
//        } catch (IllegalArgumentException e) {
//            throw new InvalidParametersException("Invalid team bet parameter in request.");
//        }
//    }
//
//    public static Date extractDate(RequestContext requestContext)
//            throws InvalidParametersException {
//        String dateStr = requestContext.getRequestParameter(Parameter.DATE);
//        try {
//            return DateParser.parse(dateStr, DateFormatType.HTML);
//        } catch (ParseException e) {
//            throw new InvalidParametersException("Invalid date parameter format.");
//        }
//    }
}
