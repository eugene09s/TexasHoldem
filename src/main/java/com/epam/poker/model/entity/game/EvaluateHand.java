package com.epam.poker.model.entity.game;

import com.epam.poker.model.entity.Entity;

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
        this.rank = "";
        this.name = "";
        this.cards = new ArrayList<>();
    }

    public String getRank() {
        return rank;
    }

    public EvaluateHand setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getName() {
        return name;
    }

    public EvaluateHand setName(String name) {
        this.name = name;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public EvaluateHand setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public List<String> getCards() {
        return cards;
    }

    public EvaluateHand setCards(List<String> cards) {
        this.cards = cards;
        return this;
    }
}
