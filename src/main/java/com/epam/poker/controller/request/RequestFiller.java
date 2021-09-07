package com.epam.poker.controller.request;

import com.epam.poker.controller.command.constant.Attribute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Set;

public class RequestFiller {

    public void fillData(HttpServletRequest request, RequestContext requestContext) {
        Set<String> requestAttributeNames = requestContext.getRequestAttributeNames();
        for (String attributeName : requestAttributeNames) {
            Object attributeValue = requestContext.getRequestAttribute(attributeName);
            request.setAttribute(attributeName, attributeValue);
        }

        HttpSession session = request.getSession();
        Set<String> sessionAttributeNames = requestContext.getSessionAttributeNames();
        if (sessionAttributeNames.contains(Attribute.INVALIDATE_ATTRIBUTE)) {
            session.invalidate();
        } else {
            for (String attributeName : sessionAttributeNames) {
                Object attributeValue = requestContext.getSessionAttribute(attributeName);
                session.setAttribute(attributeName, attributeValue);
            }
        }
    }
}
