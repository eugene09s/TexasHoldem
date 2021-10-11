package com.epam.poker.model.game;

import com.epam.poker.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class EvaluateHand implements Entity {
    private String rank;
    private String name;
    private int rating;
    private List<String> cards;

    public EvaluateHand(String rank, String name, int rating, List<String> cards) {
        this.rank = rank;
        this.name = name;
        this.rating = rating;
        this.cards = cards;
    }

    public EvaluateHand() {
        this.cards = new ArrayList<>();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluateHand that = (EvaluateHand) o;

        if (rating != that.rating) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return cards != null ? cards.equals(that.cards) : that.cards == null;
    }

    @Override
    public int hashCode() {
        int result = rank != null ? rank.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EvaluateHand{");
        sb.append("rank='").append(rank).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", cards=").append(cards);
        sb.append('}');
        return sb.toString();
    }
}
