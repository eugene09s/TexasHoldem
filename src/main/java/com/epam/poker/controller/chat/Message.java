package com.epam.poker.controller.chat;

import java.time.LocalTime;

public class Message {
    private String name;
    private String text;
    private String img;
    private String time;

    private Message() {}

    public String getName() {
        return name;
    }

    public Message setName(String name) {
        this.name = name;
        return this;
    }

    public Message setTime(String localTime) {
        this.time = localTime;
        return this;
    }

    public Message setImg(String img) {
        this.img = img;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        private Message message;

        public MessageBuilder() {
            message = new Message();
        }

        public void setName(String name) {
            message.setName(name);
        }

        public void setText(String text) {
            message.setText(text);
        }

        public Message createMessage() {
            return message;
        }
    }
}
