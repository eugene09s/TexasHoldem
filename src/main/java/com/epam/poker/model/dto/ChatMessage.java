package com.epam.poker.model.dto;

public class ChatMessage {
    private String name;
    private String text;
    private String img;
    private String time;

    private ChatMessage() {}

    public String getName() {
        return name;
    }

    public ChatMessage setTime(String localTime) {
        this.time = localTime;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage message = (ChatMessage) o;

        if (name != null ? !name.equals(message.name) : message.name != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (img != null ? !img.equals(message.img) : message.img != null) return false;
        return time != null ? time.equals(message.time) : message.time == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("name='").append(name).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", img='").append(img).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        private ChatMessage message;

        public MessageBuilder() {
            message = new ChatMessage();
        }

        public void setName(String name) {
            message.setName(name);
        }

        public void setText(String text) {
            message.setText(text);
        }

        public ChatMessage createMessage() {
            return message;
        }
    }
}
