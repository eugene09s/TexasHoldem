package com.epam.poker.game.entity;

public class EvaluateHand {
    private String rank;
    private String name;
    private int rating;
    private String[] cards;

    public EvaluateHand(String rank, String name, int rating, String[] cards) {
        this.rank = rank;
        this.name = name;
        this.rating = rating;
        this.cards = cards;
    }

    public EvaluateHand() {
        this.rank = "";
        this.name = "";
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

    public String[] getCards() {
        return cards;
    }

    public EvaluateHand setCards(String[] cards) {
        this.cards = cards;
        return this;
    }
}
