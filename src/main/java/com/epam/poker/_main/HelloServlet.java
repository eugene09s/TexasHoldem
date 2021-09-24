package com.epam.poker._main;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        Cookie[] cookies = request.getCookies();
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + c + "</h1>");
        LocalDateTime exp = LocalDateTime.now();
        out.println("<h2>" + exp + "</h2>");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                out.println("<p>"+ "MaxAge: " + cookie.getMaxAge() + "</p><br>");
            }
        }
        out.println("</body></html>");
        Cookie cookie = new Cookie("Key" + c, String.valueOf("Number-"+ c));
        cookie.setMaxAge(60*60*3 + 2*60);
        response.addCookie(cookie);
    }

    public void destroy() {
    }
}