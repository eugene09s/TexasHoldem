package com.epam.poker;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(Integer.valueOf(System.getenv("PORT")));

        tomcat.addWebapp("/", new File("src/main/webapp/").getAbsolutePath());

        tomcat.start();

        tomcat.getServer().await();
    }
}
