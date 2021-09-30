package com.epam.poker._main;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

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
//        ZoneId asiaSingapore = ZoneId.of("Europe/Minsk");
        TimeZone tz1 = TimeZone.getDefault();
        LOGGER.info(tz1);
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + c + "</h1>");
        LocalDateTime exp = LocalDateTime.now();
        out.println("<h2>" + exp + "</h2>");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                out.println("<p>"+ "MaxAge: " + cookie.getMaxAge() + "</p><br>");
            }
        }
        out.println("</body></html>");
        Cookie cookie = new Cookie("Key" + c, String.valueOf("Number-"+ c));
        cookie.setMaxAge(60*60*3 + 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void destroy() {
    }
}