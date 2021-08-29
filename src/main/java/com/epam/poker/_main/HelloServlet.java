package com.epam.poker._main;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private static int c;
    private static final Logger LOGGER = LogManager.getLogger();
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        c++;
        LOGGER.info("Value info c: " + c);
        LOGGER.warn("Value warn c: " + c);
        LOGGER.fatal("Value warn c: " + c);
        try {
            int i = 2;
            i = i / 0;
        } catch (Exception e) {
            LOGGER.error(e);
        }
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + c + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}