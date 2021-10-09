package com.epam.poker.service.game;

import com.epam.poker.model.game.EvaluateHand;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Table;

import java.util.*;

public class EvaluateHandService {
    private static final List<Character> cardNames = List.of(
            '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
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
    private static final Map<Character, String> cardsFullNameByKeyName = new HashMap<>();
    private final Map<Character, List<String>> flushes;
    private final Map<Character, List<String>> pairs;
    private final EvaluateHand evaluateHand;
    private List<String> straightCards;

    public EvaluateHandService() {
        this.straightCards = new ArrayList<>();
        this.flushes = new HashMap<>(4);
        this.evaluateHand = new EvaluateHand();
        this.pairs = new HashMap<>();
        init();
    }

    public void execute(Table table, Gambler gambler) {
        List<String> cards = new ArrayList<>(7);
        Collections.addAll(cards, table.getBoard());
        cards.add(gambler.getPrivateCards()[0]);
        cards.add(gambler.getPrivateCards()[1]);
        cards.sort((a, b) -> cardNames.indexOf(b.charAt(0)) - cardNames.indexOf(a.charAt(0)));
        flushes.get(cards.get(0).charAt(1)).add(cards.get(0));
        straightCards.add(cards.get(0));
        restOfCards(cards);
        if (straightCards.size() >= 4) {
            if (straightCards.get(straightCards.size() - 1).charAt(0) == '2'
                    && cards.get(0).charAt(0) == 'A') {
                straightCards.add(cards.get(0));
            }
            if (straightCards.size() >= 5) {
                evaluateHand.setRank(STRAIGHT);
                evaluateHand.setCards(straightCards.subList(0, 5));
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

    private void calculateCards(Integer i, Integer kickets, int rangeAmount, List<String> cards) {
        while (kickets < rangeAmount) {
            if (cards.get(i).charAt(0) != evaluateHand.getCards().get(0).charAt(0)) {
                evaluateHand.getCards().add(cards.get(i));
                kickets++;
            }
            ++i;
        }
    }

    private void handlerForOnePairCard(List<String> cards) {
        Integer kickets = 0;
        Integer i = 0;
        for (Map.Entry<Character, List<String>> entry : pairs.entrySet()) {
            evaluateHand.setCards(entry.getValue());
        }
        if (evaluateHand.getCards().size() == 2) {
            evaluateHand.setRank(PAIR);
            calculateCards(i, kickets,3, cards);
        } else if (evaluateHand.getCards().size() == 3) {
            evaluateHand.setRank(THREE_OF_KIND);
            calculateCards(i, kickets,2, cards);
        } else if (evaluateHand.getCards().size() == 4) { //if it is a four a kind
            evaluateHand.setRank(FOUR_OF_KIND);
            calculateCards(i, kickets,1, cards);
        }
    }

    private void handlerForTwoPairsCard(List<String> cards) {
        int kickets = 0;
        int i = 0;
        List<Character> pairKeys = new ArrayList<>();
        for (Map.Entry<Character, List<String>> entry : pairs.entrySet()) {
            pairKeys.add(entry.getKey());
        }
        // Add to the evaluated hand, the pair with the greatest value
        if (pairs.get(pairKeys.get(0)).size() > pairs.get(pairKeys.get(1)).size()
                || (pairs.get(pairKeys.get(0)).size() == pairs.get(pairKeys.get(1)).size())
                && cardNames.indexOf(pairKeys.get(0)) > cardNames.indexOf(pairKeys.get(1))) {
            evaluateHand.setCards(pairs.get(pairKeys.get(0)));
            pairs.remove(pairKeys.get(0));
        } else {
            evaluateHand.setCards(pairs.get(pairKeys.get(1)));
            pairs.remove(pairKeys.get(1));
        }
        // If the biggest pair has two cards
        if (evaluateHand.getCards().size() == 2) {
            // Add the other two cards to the evaluated hand
            for (int j = 0; j < 2; ++j) {
                evaluateHand.getCards().add(pairs.get(pairKeys.get(0)).get(j));
            }
            evaluateHand.setRank(TWO_PAIR);
            // Add one kicker
            while (kickets < 1) {
                if (cards.get(i).charAt(0) != evaluateHand.getCards().get(0).charAt(0)
                        && cards.get(i).charAt(0) != evaluateHand.getCards().get(2).charAt(0)) {
                    evaluateHand.getCards().add(cards.get(i));
                    kickets++;
                }
                ++i;
            }
        } else if (evaluateHand.getCards().size() == 3) {
            evaluateHand.setRank(FULL_HOUSE);
            for (int j = 0; j < 2; ++j) {
                evaluateHand.getCards().add(pairs.get(pairKeys.get(0)).get(j));
            }
            // If the biggest pair has four cards
        } else {
            evaluateHand.setRank(FOUR_OF_KIND);
            calculateCards(i, kickets,1, cards);
        }
    }

    private void handlerForThreePaisCard(List<String> cards) {
        int kickets = 0;
        int i = 0;
        List<Character> pairKeys = new ArrayList<>();
        // If there is a pair with three cards, it's the biggest pair
        for (Map.Entry<Character, List<String>> entry : pairs.entrySet()) {
            pairKeys.add(entry.getKey());
            if (entry.getValue().size() == 3) {
                evaluateHand.setRank(FULL_HOUSE);
                evaluateHand.setCards(entry.getValue());
                pairs.remove(entry.getKey());
                break;
            }
        }
        //Else, there are three pairs of two cards, so find the biggest one
        if (evaluateHand.getCards().isEmpty()) {
            evaluateHand.setRank(TWO_PAIR);
            if (cardNames.indexOf(pairKeys.get(0)) > cardNames.indexOf(pairKeys.get(1))) {
                if (cardNames.indexOf(pairKeys.get(0)) > cardNames.indexOf(pairKeys.get(2))) {
                    evaluateHand.setCards(pairs.get(pairKeys.get(0)));
                    pairs.remove(pairKeys.get(0));
                } else {
                    evaluateHand.setCards(pairs.get(pairKeys.get(2)));
                    pairs.remove(pairKeys.get(2));
                }
            } else {
                if (cardNames.indexOf(pairKeys.get(1)) > cardNames.indexOf(pairKeys.get(2))) {
                    evaluateHand.setCards(pairs.get(pairKeys.get(1)));
                    pairs.remove(pairKeys.get(2));
                } else {
                    evaluateHand.setCards(pairs.get(pairKeys.get(2)));
                    pairs.remove(pairKeys.get(2));
                }
            }
        }
        pairKeys = new ArrayList<>();
        for (Map.Entry<Character, List<String>> entry : pairs.entrySet()) {
            pairKeys.add(entry.getKey());
        }
        //Adding the second biggest pair in the hand
        if (cardNames.indexOf(pairKeys.get(0)) > cardNames.indexOf(pairKeys.get(1))) {
            for (int j = 0; j < 2; ++j) {
                evaluateHand.getCards().add(pairs.get(pairKeys.get(0)).get(j));
            }
        } else {
            for (int j = 0; j < 2; ++j) {
                evaluateHand.getCards().add(pairs.get(pairKeys.get(1)).get(j));
            }
        }
        // If the biggest pair has two cards, add one kicker
        if (TWO_PAIR.equals(evaluateHand.getRank())) {
            while (kickets < 1) {
                if (cards.get(i).charAt(0) != evaluateHand.getCards().get(0).charAt(0)
                        && cards.get(i).charAt(0) != evaluateHand.getCards().get(2).charAt(0)) {
                    evaluateHand.getCards().add(cards.get(i));
                    kickets++;
                }
                ++i;
            }
        }
    }

    private void evaluatePairsHandCards(List<String> cards) {
        int numberOfPairs = pairs.size();
        if (numberOfPairs == 1) {
            handlerForOnePairCard(cards);
        } else if (numberOfPairs == 2) { //if there are two pairs
            handlerForTwoPairsCard(cards);
        } else { // If there are three pairs
            handlerForThreePaisCard(cards);
        }
    }

    private void evaluateFlushHandCards(List<String> cards) {
        for (Map.Entry<Character, List<String>> entry : flushes.entrySet()) {
            int flushLength = entry.getValue().size();
            if (flushLength >= 5) {
                if (STRAIGHT.equals(evaluateHand.getRank())) {
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
                            && cards.contains("A" + entry.getKey())) {
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
                if (STRAIGHT_FLUSH.equals(evaluateHand.getRank())
                        && ROYAL_FLUSH.equals(evaluateHand.getRank())) {
                    evaluateHand.setRank(FLUSH);
                    evaluateHand.setCards(entry.getValue().subList(0, 5));
                }
                break;
            }
        }
    }

    private void calcEvaluateHand() {
        switch (evaluateHand.getRank()) {
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
            default -> {//High card
                evaluateHand.setName(
                        findCardNameByKey(evaluateHand.getCards().get(0).charAt(0), false));
                evaluateHand.setRating(calcRateHand(evaluateHand.getCards()));
            }
        }
    }

    public void restOfCards(List<String> cards) {
        for (int i = 1; i < 7; ++i) {
            flushes.get(cards.get(i).charAt(1)).add(cards.get(i));
            int currentCardValue = cardNames.indexOf(cards.get(i).charAt(0));
            int previousCardValue = cardNames.indexOf(straightCards.get(straightCards.size() - 1).charAt(0));
            // If the current value is smaller than the value of the prevous card by one,
            // push it to the straight array
            if (currentCardValue + 1 == previousCardValue) {
                straightCards.add(cards.get(i));
            } else if (currentCardValue != previousCardValue && straightCards.size() < 5) {
                straightCards = new ArrayList<>();
                straightCards.add(cards.get(i));//perhapse bug
            } else if (currentCardValue == previousCardValue) {
                if (!pairs.containsKey(cards.get(i).charAt(0))) {
                    String[] pair = new String[]{cards.get(i - 1), cards.get(i)};
                    List<String> listPair = new ArrayList<>();
                    listPair.add(pair[0]);
                    listPair.add(pair[1]);
                    pairs.put(cards.get(i).charAt(0), listPair);
                } else {
                    pairs.get(cards.get(i).charAt(0)).add(cards.get(i));
                }
            }
        }
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
        cardsFullNameByKeyName.put('A', "ace");
        cardsFullNameByKeyName.put('K', "king");
        cardsFullNameByKeyName.put('Q', "queen");
        cardsFullNameByKeyName.put('J', "jack");
        cardsFullNameByKeyName.put('T', "ten");
        cardsFullNameByKeyName.put('9', "nine");
        cardsFullNameByKeyName.put('8', "eight");
        cardsFullNameByKeyName.put('7', "seven");
        cardsFullNameByKeyName.put('6', "six");
        cardsFullNameByKeyName.put('5', "five");
        cardsFullNameByKeyName.put('4', "four");
        cardsFullNameByKeyName.put('3', "three");
        cardsFullNameByKeyName.put('2', "deuce");
    }
}
