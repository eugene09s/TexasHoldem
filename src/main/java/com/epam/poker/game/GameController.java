package com.epam.poker.game;

import com.epam.poker.game.entity.Table;
import com.epam.poker.game.lobby.Lobby;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/lobby-data"}, name = "gamePokerServlet")
public class GameController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lobby lobby = Lobby.getInstance();
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> lobbyTables = new ArrayList<>();
        List<Table> tables = lobby.findAllTables();
        for (Table table : tables) {
            lobbyTables.add(gson.toJson(table));
        }
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(lobbyTables);
        out.flush();

    }
}
