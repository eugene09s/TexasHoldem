package com.epam.poker.util;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.List;
import java.util.Map;

public class EndpointAwareConfig extends ServerEndpointConfig.Configurator {
    private static final String LINE_COOKIE = "cookie";

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        Map<String,List<String>> headers = request.getHeaders();
        config.getUserProperties().put(LINE_COOKIE,headers.get(LINE_COOKIE));
    }
}
