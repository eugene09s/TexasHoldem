package com.epam.poker.model.game;

import com.epam.poker.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Deck implements Entity {
    private static final int SIZE_DECK = 52;
    private static final List<String> FULL_DECK_CARDS = new ArrayList<>(List.of(
            "As", "Ah", "Ad", "Ac",
            "Ks", "Kh", "Kd", "Kc",
            "Qs", "Qh", "Qd", "Qc",
            "Js", "Jh", "Jd", "Jc",
            "Ts", "Th", "Td", "Tc",
            "9s", "9h", "9d", "9c",
            "8s", "8h", "8d", "8c",
            "7s", "7h", "7d", "7c",
            "6s", "6h", "6d", "6c",
            "5s", "5h", "5d", "5c",
            "4s", "4h", "4d", "4c",
            "3s", "3h", "3d", "3c",
            "2s", "2h", "2d", "2c"
    ));
    private List<String> freshDeck;
    private int nextCard;

    public Deck() {
    }

    public void shuffle() {
        List<String> cards = new ArrayList<>(FULL_DECK_CARDS);
        List<String> shuffledDeck = new ArrayList<>(SIZE_DECK);
        String randomCard;
        while (cards.size() > 0){
            randomCard = cards.remove((int) (Math.random()*( cards.size())));
            shuffledDeck.add(randomCard);
        }
        this.freshDeck = shuffledDeck;
    }

    public List<String> getFullDeck() {
        return FULL_DECK_CARDS;
    }

    public List<String> getFreshDeck() {
        return this.freshDeck;
    }

    public List<String> pullSomeCardsFromDeck(int numberOfCards) {
        List<String> dealtCards = new ArrayList<>(numberOfCards);
        for (int i = 0; i < numberOfCards && this.nextCard < SIZE_DECK; ++i) {
            dealtCards.add(this.freshDeck.get(this.nextCard));
            this.nextCard++;
        }
        return dealtCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        if (nextCard != deck.nextCard) return false;
        return freshDeck != null ? freshDeck.equals(deck.freshDeck) : deck.freshDeck == null;
    }

    @Override
    public int hashCode() {
        int result = freshDeck != null ? freshDeck.hashCode() : 0;
        result = 31 * result + nextCard;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deck{");
        sb.append("freshDeck=").append(freshDeck);
        sb.append(", nextCard=").append(nextCard);
        sb.append('}');
        return sb.toString();
    }
}
