package com.epam.poker.controller.chat;

import com.epam.poker.controller.command.constant.Attribute;
import jakarta.servlet.http.Cookie;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServletAwareConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        Map<String,List<String>> headers = request.getHeaders();
        config.getUserProperties().put("cookie",headers.get("cookie"));
    }

    private Optional<Cookie> getTokenFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(Attribute.ACCESS_TOKEN))
                .findFirst();
    }
}
