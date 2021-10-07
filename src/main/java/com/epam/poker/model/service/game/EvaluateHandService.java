package com.epam.poker.model.service.game;

import com.epam.poker.model.entity.game.EvaluateHand;
import com.epam.poker.model.entity.game.Gambler;
import com.epam.poker.model.entity.game.Table;

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
        if (straight.size() >= 4) {
            if (straight.get(straight.size() - 1).charAt(0) == '2'
                    && cards.get(0).charAt(0) == 'A') {
                straight.add(cards.get(0));
            }
            if (straight.size() >= 5) {
                evaluateHand.setRank(STRAIGHT);
                evaluateHand.setCards(straight.subList(0, 5));
            }
        }
        evaluateFlushHandCards(cards);
        if (evaluateHand.getRank() == null && pairs.size() > 0) {
            evaluatePairsHandCards(cards);
        }
        if (evaluateHand.getRank() == null) {
            evaluateHand.setRank(HIGH_CARD);
            evaluateHand.setCards(cards.subList(0, 5));
        }
        calcEvaluateHand();
        gambler.setEvaluateHand(this.evaluateHand);
    }

    private void evaluatePairsHandCards(List<String> cards) {
        int numberOfPairs = pairs.size();
        int kickets = 0;
        int i = 0;
        if (numberOfPairs == 1) {
            for (Map.Entry<Character, String[]> entry : pairs.entrySet()) {
                evaluateHand.setCards(List.of(entry.getValue()));
            }
            if (evaluateHand.getCards().size() == 2) {
                evaluateHand.setRank(PAIR);
                while (kickets < 3) {
                    if (cards.get(i).charAt(0) != evaluateHand.getCards().get(0).charAt(0)) {
                        evaluateHand.getCards().add(cards.get(i));
                        kickets++;
                    }
                    ++i;
                }
            } else if (evaluateHand.getCards().size() == 3) {
                evaluateHand.setRank(THREE_OF_KIND);
                while (kickets < 2) {
                    if (cards.get(i).charAt(0) != evaluateHand.getCards().get(0).charAt(0)) {
                        evaluateHand.getCards().add(cards.get(i));
                        kickets++;
                    }
                    i++;
                }
            } else if (evaluateHand.getCards().size() == 4) { //if it is a four a kind
                evaluateHand.setRank(FOUR_OF_KIND);
                while (kickets < 1) {
                    if (cards.get(i).charAt(0) != evaluateHand.getCards().get(0).charAt(0)) {
                        evaluateHand.getCards().add(cards.get(i));
                        kickets++;
                    }
                    i++;
                }
            }
        } else if (numberOfPairs == 2) { //if there are two pairs
            //Add to the evaluated hand, the pair with the greatest value
//                if (pairs)
            //todo should be doing
        } else { //if there are three pairs
            //todo should be doing
        }
    }

    private void evaluateFlushHandCards(List<String> cards) {
        for (Map.Entry<Character, List<String>> entry : flushes.entrySet()) {
            int flushLength = entry.getValue().size();
            if (flushLength >= 5) {
                if (evaluateHand.getRank().equals(STRAIGHT)) {
                    List<String> straightFlush = new ArrayList<>();
                    straightFlush.add(entry.getValue().get(0));
                    int j = 1;
                    while (j < flushLength && straightFlush.size() < 5) {
                        int currentCardValue = cardNames.indexOf(entry.getValue().get(j).charAt(0));
                        int previousCardValue = cardNames.indexOf(entry.getValue().get(j - 1).charAt(0));
                        if (currentCardValue + 1 == previousCardValue) {
                            straightFlush.add(entry.getValue().get(j));
                        } else if (currentCardValue != previousCardValue && straightFlush.size() < 5) {
                            straightFlush = new ArrayList<>();
                            straightFlush.add(entry.getValue().get(j));
                        }
                        ++j;
                    }
                    if (straightFlush.size() == 4 && straightFlush.get(3).charAt(0) == '2'
                            && cards.indexOf("A" + entry.getKey()) >= 0) {
                        straightFlush.add("A" + entry.getKey());
                    }
                    if (straightFlush.size() == 5) {
                        evaluateHand.setCards(straightFlush);
                        if (evaluateHand.getCards().get(0).charAt(0) == 'A') {
                            evaluateHand.setRank(ROYAL_FLUSH);
                        } else {
                            evaluateHand.setRank(STRAIGHT_FLUSH);
                        }
                    }
                }
                if (evaluateHand.getRank() != STRAIGHT_FLUSH && evaluateHand.getRank() != ROYAL_FLUSH) {
                    evaluateHand.setRank(FLUSH);
                    evaluateHand.setCards(entry.getValue().subList(0, 5));
                }
                break;
            }
        }
    }

    private void calcEvaluateHand() {
        switch (evaluateHand.getRank()) {
            case HIGH_CARD -> {
                evaluateHand.setName(
                        findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), false));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards()));
            }
            case PAIR -> {
                evaluateHand.setName("a pair of "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 1000000);
            }
            case TWO_PAIR -> {
                evaluateHand.setName("two pair, "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true)
                        + " and " + findCardNameByKey(evaluateHand.getCards().get(2).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 2000000);
            }
            case THREE_OF_KIND -> {
                evaluateHand.setName("three of a kind, "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 3000000);
            }
            case STRAIGHT -> {
                evaluateHand.setName("a straight to "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 4000000);
            }
            case FLUSH -> {
                evaluateHand.setName("a flush, "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true)
                        + " high");
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 5000000);
            }
            case FULL_HOUSE -> {
                evaluateHand.setName("a full house, "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true)
                        + " full of "
                        + findCardNameByKey(evaluateHand.getCards().get(3).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 6000000);
            }
            case FOUR_OF_KIND -> {
                evaluateHand.setName("four of a kind, "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 7000000);
            }
            case STRAIGHT_FLUSH -> {
                evaluateHand.setName("a straight flush, "
                        + findCardNameByKey(evaluateHand.getCards().get(4).charAt(0), true)
                        + " to "
                        + findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), true));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 8000000);
            }
            case ROYAL_FLUSH -> {
                evaluateHand.setName("a royal flush");
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards())
                        + 8000000);
            }
        }
    }

    private void restOfCards(List<String> cards) {
        for (int i = 0; i < 7; ++i) {
            flushes.get(cards.get(i).charAt(1)).add(cards.get(i));
            int currentCardValue = cardNames.indexOf(cards.get(i).charAt(0));
            int previousCardValue = cardNames.indexOf(straight.get(straight.size() - 1).charAt(0));
            if (currentCardValue + 1 == previousCardValue) {
                straight.add(cards.get(i));
            } else if (currentCardValue != previousCardValue && straight.size() < 5) {
                straight.add(cards.get(i));//perhapse bug
            } else if (currentCardValue == previousCardValue) {
                if (!pairs.containsKey(cards.get(i).charAt(0))) {
                    String[] pair = new String[]{cards.get(i - 1), cards.get(i)};
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

    private int calcRateHand(List<String> cards) {
        return cardNames.indexOf(cards.get(0).charAt(0)) * 30941
                + cardNames.indexOf(cards.get(1).charAt(0)) * 2380
                + cardNames.indexOf(cards.get(2).charAt(0)) * 183
                + cardNames.indexOf(cards.get(3).charAt(0)) * 14
                + cardNames.indexOf(cards.get(4).charAt(0));
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
