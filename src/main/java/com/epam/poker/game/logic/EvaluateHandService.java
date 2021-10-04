package com.epam.poker.game.logic;

import com.epam.poker.game.entity.EvaluateHand;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Table;

import java.util.*;

public class EvaluateHandService {
    private static final Map<String, String> cardsFullNameByKeyName = new HashMap<String, String>() {{
        put("A", "ace");
        put("K", "king");
        put("Q", "queen");
        put("J", "jack");
        put("T", "ten");
        put("9", "nine");
        put("8", "eight");
        put("7", "seven");
        put("6", "six");
        put("5", "five");
        put("4", "four");
        put("3", "three");
        put("2", "deuce");
    }};
    private static final List<String> cardNames = List.of(
            "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A");
    private static final char SPADES_SUIT = 's';
    private static final char HEARTS_SUIT = 'h';
    private static final char DIAMONDS_SUIT = 'd';
    private static final char CLUBS_SUIT = 'c';
    private static final String HIGH_CARD = "high card";
    private static final String PAIR = "pair";
    private static final String TWO_PAIR = "two pair";
    private static final String THREE_OF_KIND = "three of a kind";
    private static final String STRAIGHT = "straight";
    private static final String FLUSH = "flush";
    private static final String FULL_HOUSE = "full house'";
    private static final String FOUR_OF_KIND = "four of a kind";
    private static final String STRAIGHT_FLUSH = "straight flush";
    private static final String ROYAL_FLUSH = "royal flush";
    private List<String> straight;
    private Map<Character, List<String>> flushes;
    private Map<Character, String[]> pairs;
    private EvaluateHand evaluateHand;

    public EvaluateHandService() {
        this.straight = new ArrayList<>();
        this.flushes = new HashMap<>(4);
        this.evaluateHand = new EvaluateHand();
        this.pairs = new HashMap<>();
        init();
    }

    public void execute(Table table, Gambler gambler) {
        List<String> cards = List.of(table.getBoard());
        cards.add(gambler.getPrivateCards()[0]);
        cards.add(gambler.getPrivateCards()[1]);
        flushes.get(cards.get(0).charAt(1)).add(cards.get(0));
        straight.add(cards.get(0));
        restOfCards(cards);

    }

    private void calcEvaluateHand() {
        switch (evaluateHand.getRank()) {
            case HIGH_CARD -> {
                evaluateHand.setName(
                        findCardNameByKey(evaluateHand.getCards()[0].charAt(0), false));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards()));
            }
            case PAIR -> {
                evaluateHand.setName("a pair of "
                        + findCardNameByKey(evaluateHand.getCards()[0].charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 1000000);
            }
            case TWO_PAIR -> {

            }
        }
    }

    private void restOfCards(List<String> cards) {
        for (int i = 0; i < 7; ++i) {
            flushes.get(cards.get(i).charAt(1)).add(cards.get(i));
            int currentCardValue = cardNames.indexOf(cards.get(i).charAt(0));
            int previousCardValue = cardNames.indexOf(straight.get(straight.size()-1).charAt(0));
            if (currentCardValue + 1 == previousCardValue) {
                straight.add(cards.get(i));
            } else if (currentCardValue != previousCardValue && straight.size() < 5) {
                straight.add(cards.get(i));//perhapse bug
            } else if (currentCardValue == previousCardValue) {
                if (!pairs.containsKey(cards.get(i).charAt(0))) {
                    String[] pair = new String[]{cards.get(i-1), cards.get(i)};
                    pairs.put(cards.get(i).charAt(0), pair);
                } else {
                    String[] card = new String[]{cards.get(i)};
                    pairs.put(cards.get(i).charAt(0), card);
                }
            }
        }
    }

    private int sort(String cardFirst, String cardSecond) {
        return cardNames.indexOf(cardFirst.charAt(0)) - cardNames.indexOf(cardSecond.charAt(0));
    }

    private int calcRateHand(String[] cards) {
        return cardNames.indexOf(cards[0].charAt(0)) * 30941
                + cardNames.indexOf(cards[1].charAt(0)) * 2380
                + cardNames.indexOf(cards[2].charAt(0)) * 183
                + cardNames.indexOf(cards[3].charAt(0)) * 14
                + cardNames.indexOf(cards[4].charAt(0));
    }

    private void swapCardsBySmaller(String[] cards, int index1, int index2) {
        if (cardNames.indexOf(cards[index1].charAt(0)) < cardNames.indexOf(cards[index2].charAt(0))) {
            String tmp = cards[index1];
            cards[index1] = cards[index2];
            cards[index2] = tmp;
        }
    }

    private String findCardNameByKey(char cardValue, boolean plural) {
        if (plural) {
            return cardValue == '6' ? cardsFullNameByKeyName.get(cardValue) + "es"
                    : cardsFullNameByKeyName.get(cardValue) + "s";
        } else {
            return cardsFullNameByKeyName.get(cardValue);
        }
    }

    private void init() {
        this.flushes.put(SPADES_SUIT, new ArrayList<>());
        this.flushes.put(HEARTS_SUIT, new ArrayList<>());
        this.flushes.put(DIAMONDS_SUIT, new ArrayList<>());
        this.flushes.put(CLUBS_SUIT, new ArrayList<>());
    }
}
