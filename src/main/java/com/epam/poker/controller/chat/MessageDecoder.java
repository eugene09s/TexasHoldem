package com.epam.poker.controller.chat;

import com.google.gson.Gson;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {
    private static Gson gson = new Gson();

    @Override
    public Message decode(String s) throws DecodeException {
        return gson.fromJson(s, Message.class);
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
