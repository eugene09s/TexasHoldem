package com.epam.poker.model.dto;

import com.epam.poker.model.Entity;

public class ChatMessage implements Entity {
    private String name;
    private String text;
    private String img;
    private String time;
    private boolean isOwner;

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

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        if (isOwner != that.isOwner) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (isOwner ? 1 : 0);
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
}
