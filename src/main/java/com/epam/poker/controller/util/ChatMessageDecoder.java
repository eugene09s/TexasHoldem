package com.epam.poker.controller.util;

import com.epam.poker.model.dto.ChatMessage;
import com.google.gson.Gson;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
    private static final Gson gson = new Gson();

    @Override
    public ChatMessage decode(String s) throws DecodeException {
        return gson.fromJson(s, ChatMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig config) {
        Text.super.init(config);
    }

    @Override
    public void destroy() {
        Text.super.destroy();
    }
}
