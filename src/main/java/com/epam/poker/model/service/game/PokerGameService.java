package com.epam.poker.model.service.game;

import com.epam.poker.model.entity.game.*;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class PokerGameService {
    private static final PokerGameService instance = new PokerGameService();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TWO_GAMBLERS = 2;
    private static final int TWO_CARDS = 2;
    private static final String SMALL_BLIND = "smallBlind";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BIG_BLIND = "bigBlind";
    private static final String FIRST_CARD = "firstCard";
    private static final String SECOND_CARD = "secondCard";
    private static final String DEALING_CARDS_EVENT = "dealingCards";
    private static PotService potService = PotService.getInstance();
    private static NotifierTableDataService notifierTableDataService = NotifierTableDataService.getInstance();

    private PokerGameService() {
    }

    public static PokerGameService getInstacne() {
        return instance;
    }

    public void initializePreflop(Table table) {
        table.setPhaseGame(Attribute.PRE_FLOP_PART_OF_GAME);
        int currentGambler = table.getActiveSeat();
        table.setLastGamblerToAct(table.getActiveSeat());
        for (int i = 0; i < table.getGamblersInHandCount(); ++i) {
            Gambler gambler = table.getSeats()[currentGambler];
            gambler.setPrivateCards(table.getDeck().pullSomeCardsFromDeck(2).toArray(new String[0]));
            gambler.setHasCards(true);
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put(FIRST_CARD, gambler.getPrivateCards()[0]);
            objectNode.put(SECOND_CARD, gambler.getPrivateCards()[1]);
            notifierTableDataService.notifyGambler(DEALING_CARDS_EVENT, gambler, objectNode);
            currentGambler = findNextGambler(table, String.valueOf(currentGambler), true, false);
        }
        actionToNextGambler(table);
    }

    public void initializeBigBlind(Table table) {
        table.setPhaseGame(BIG_BLIND);
        actionToNextGambler(table);
    }

    public void initializeRound(Table table, boolean changeDealer) {
        if (table.getGamblersSittingInCount() > 1) {
            table.setGameIsOn(true);
            table.setBoard(new String[]{"", "", "", "", ""});
            table.getDeck().shuffle();
            table.setHeadsUp(table.getGamblersSittingInCount() == TWO_GAMBLERS);
            table.setGamblersInHandCount(0);
            for (int i = 0; i < table.getSeatsCount(); ++i) {
                //if a player is sitting on the current seat
                if (table.getSeats()[i] != null
                        && table.getSeats()[i].isSittingIn()) {
                    if (table.getSeats()[i] != null
                            && table.getSeats()[i].getMoneyInPlay().compareTo(BigDecimal.ZERO) == 0) {
                        table.getSeats()[i].sitOut();
                        int playersSittinCount = table.getGamblersSittingInCount() - 1;
                        table.setGamblersSittingInCount(playersSittinCount);
                    } else {
                        int playersInHandCount = table.getGamblersInHandCount() + 1;
                        table.setGamblersInHandCount(playersInHandCount);
                        prepareForNewRound(table.getSeats()[i]);
                    }
                }
            }

            //Giving thr dealer button to a room player
            if (table.getDealerSeat() == null) {
                int randomDealerSeat = (int) Math.ceil(Math.random() * table.getGamblersSittingInCount());
                int playerCount = 0;
                int i = -1;
                //Assigning the dealer button to the random gambler
                while (playerCount != randomDealerSeat && i < table.getSeatsCount()) {
                    ++i;
                    if (table.getSeats()[i] != null
                            && table.getSeats()[i].isSittingIn()) {
                        ++playerCount;
                    }
                }
                table.setDealerSeat(i);
            } else if (changeDealer || !table.getSeats()[table.getDealerSeat()].isSittingIn()) {
                //if the dealer should be changed because the game will start with a new player
                //or if the old dealer is sitting out, give the dealer button to the next player
                table.setDealerSeat(findNextGambler(table, String.valueOf(table.getDealerSeat()), true, false));
            }
            initializeSmallBlind(table);
        }
    }

    public void initializeSmallBlind(Table table) {
        table.setPhaseGame(SMALL_BLIND);
        if (table.isHeadsUp()) {
            table.setActiveSeat(table.getDealerSeat());
        } else {
            table.setActiveSeat(findNextGambler(table, String.valueOf(table.getDealerSeat()), true, false));
        }
        table.setLastGamblerToAct(10);
        Gambler gambler = table.getSeats()[table.getActiveSeat()];
        notifierTableDataService.notifyGambler(Attribute.POST_SMALL_BLIND_EVENT, gambler, null);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
    }

    private void prepareForNewRound(Gambler gambler) {
        gambler.setPrivateCards(new String[TWO_CARDS]);
        gambler.setPublicCards(new String[TWO_CARDS]);
        gambler.setHasCards(false);
        gambler.setBet(BigDecimal.ZERO);
        gambler.setInHand(true);
        gambler.setEvaluateHand(new EvaluateHand());
    }

    public void endPhase(Table table) {
        switch (table.getPhaseGame()) {
            case "preflop", "flop", "turn" -> initializeNextPhase(table);
            case "river" -> showdown(table);
        }
    }

    private void initializeNextPhase(Table table) {
        switch (table.getPhaseGame()) {
            case Attribute.PRE_FLOP_PART_OF_GAME -> {
                table.setPhaseGame(Attribute.FLOP_PART_OF_GAME);
                Deck deck = table.getDeck();
                String[] cards = deck.pullSomeCardsFromDeck(3).toArray(new String[0]);
                table.getBoard()[0] = cards[0];
                table.getBoard()[1] = cards[1];
                table.getBoard()[2] = cards[2];
            }
            case Attribute.FLOP_PART_OF_GAME -> {
                table.setPhaseGame(Attribute.TURN_PART_OF_GAME);
                Deck deck = table.getDeck();
                table.getBoard()[3] =  deck.pullSomeCardsFromDeck(1).get(0);;
            }
            case Attribute.TURN_PART_OF_GAME -> {
                table.setPhaseGame(Attribute.RIVER_PART_OF_GAME);
                Deck deck = table.getDeck();
                table.getBoard()[4] = deck.pullSomeCardsFromDeck(1).get(0);
            }
        }
        potService.addTableBets(table);
        table.setBiggestBet(BigDecimal.ZERO);

        table.setActiveSeat(findNextGambler(table, String.valueOf(table.getDealerSeat()), true, false));
        table.setLastGamblerToAct(
                findPreviousGambler(table, table.getActiveSeat(), true, false));
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        //if all other gamblers are all in, there should be on actions. Move to the next round.
        if (otherGamblersAreAllIn(table)) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LOGGER.error("Sleep thread err:" + e);
            }
            endPhase(table);
        } else {
            Gambler[] gamblers = table.getSeats();
            Gambler gambler = gamblers[table.getActiveSeat()];
            notifierTableDataService.notifyGambler(Attribute.ACT_NOT_BETTED_POT_EVENT, gambler, null);
        }
    }

    public int findNextGambler(Table table, String lineOffset, boolean checkInHand, boolean checkMoneyInGame) {
        int offset = Integer.parseInt(lineOffset);
        if (checkInHand && checkMoneyInGame) {
            if (offset != table.getSeatsCount()) {
                for (int i = offset + 1; i < table.getSeatsCount(); ++i) {
                    if (table.getSeats()[i] != null) {
                        boolean validStatus = true;
                        if (checkInHand) {
                            validStatus = table.getSeats()[i].isInHand();
                        }
                        if (checkMoneyInGame) {
                            validStatus = table.getSeats()[i].getMoneyInPlay().compareTo(BigDecimal.ZERO) > 0 ? true : false;
                        }
                        if (validStatus) {
                            return i;
                        }
                    }
                }
            }
            for (int i = 0; i <= offset; ++i) {
                if (table.getSeats()[i] != null) {
                    boolean validStatus = true;
                    if (checkInHand) {
                        validStatus = table.getSeats()[i].isInHand();
                    }
                    if (checkMoneyInGame) {
                        validStatus = table.getSeats()[i].getMoneyInPlay().compareTo(BigDecimal.ZERO) > 0 ? true : false;
                    }
                    if (validStatus) {
                        return i;
                    }
                }
            }
        } else {
            if (offset != table.getSeatsCount()) {
                for (int i = offset + 1; i < table.getSeatsCount(); ++i) {
                    if (table.getSeats()[i] != null && table.getSeats()[i].isInHand()) {
                        return i;
                    }
                }
            }
            for (int i = 0; i <= offset; ++i) {
                if (table.getSeats()[i] != null && table.getSeats()[i].isInHand()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean otherGamblersAreAllIn(Table table) {
        int currentGambler = table.getActiveSeat();
        int gamblerAlIn = 0;
        for (int i = 0; i < table.getGamblersInHandCount(); ++i) {
            if (table.getSeats()[currentGambler].getMoneyInPlay().equals(BigDecimal.ZERO)) {
                gamblerAlIn++;
            }
            currentGambler = findNextGambler(table, String.valueOf(currentGambler), true, false);
        }
        return gamblerAlIn >= table.getGamblersInHandCount() - 1;
    }

    private void showdown(Table table) {
        potService.addTableBets(table);
        int currentGambler = findNextGambler(table, String.valueOf(table.getDealerSeat()), true, false);
        int bestHandRating = 0;
        for (int i = 0; i < table.getGamblersInHandCount(); ++i) {
            EvaluateHandService evaluateHandService = new EvaluateHandService();
            evaluateHandService.execute(table, table.getSeats()[currentGambler]);
            if (table.getSeats()[currentGambler].getEvaluateHand().getRating() > bestHandRating) {
                bestHandRating = table.getSeats()[currentGambler].getEvaluateHand().getRating();
            }
            table.getSeats()[currentGambler].setPublicCards(table.getSeats()[currentGambler].getPrivateCards());
            currentGambler = findNextGambler(table, String.valueOf(currentGambler), true, false);
        }
        List<String> message = potService.destributeToWinners(table, currentGambler);
        for (String msg : message) {
            Log log = Log.builder()
                    .setMessage(msg)
                    .setAction("")
                    .setSeat("")
                    .setNotification("")
                    .createLog();
            table.setLog(log);
            notifierTableDataService.notifyALLGamblersOfRoom(table);
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            LOGGER.error("Sleep err:" + e);
        }
        endRound(table);
    }

    public int findPreviousGambler(Table table, int offset, boolean checkInHand, boolean checkMoneyInGame) {
        if (checkInHand || checkMoneyInGame) {
            if (offset != 0) {
                for (int i = offset - 1; i >= 0; --i) {
                    if (table.getSeats()[i] != null) {
                        boolean validStatus = true;
                        if (checkInHand) {
                            validStatus = table.getSeats()[i].isInHand();
                        }
                        if (checkMoneyInGame) {
                            validStatus = table.getSeats()[i].getMoneyInPlay().compareTo(BigDecimal.ZERO) > 0 ? true : false;
                        }
                        if (validStatus) {
                            return i;
                        }
                    }
                }
            }
            for (int i = table.getSeatsCount() - 1; i >= offset; --i) {
                if (table.getSeats()[i] != null) {
                    boolean validStatus = true;
                    if (checkInHand) {
                        validStatus = table.getSeats()[i].isInHand();
                    }
                    if (checkMoneyInGame) {
                        validStatus = table.getSeats()[i].getMoneyInPlay().compareTo(BigDecimal.ZERO) > 0 ? true : false;
                    }
                    if (validStatus) {
                        return i;
                    }
                }
            }
        } else {
            if (offset != 0) {
                for (int i = offset - 1; i >= 0; --i) {
                    if (table.getSeats()[i] != null && table.getSeats()[i].isInHand()) {
                        return i;
                    }
                }
            }
            for (int i = table.getSeatsCount() - 1; i >= offset; --i) {
                if (table.getSeats()[i] != null && table.getSeats()[i].isInHand()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void actionToNextGambler(Table table) {
        table.setActiveSeat(findNextGambler(table, String.valueOf(table.getActiveSeat()),
                true, true));

        switch (table.getPhaseGame()) {
            case Attribute.SMALL_BLIND_PART_OF_GAME -> {
                Gambler gambler = table.getSeats()[table.getActiveSeat()];
                notifierTableDataService.notifyGambler(Attribute.POST_SMALL_BLIND_EVENT, gambler, null);
            }
            case Attribute.BIG_BLIND_PART_OF_GAME -> {
                Gambler gambler = table.getSeats()[table.getActiveSeat()];
                notifierTableDataService.notifyGambler(Attribute.POST_BIG_BLIND_EVENT, gambler, null);
            }
            case Attribute.PRE_FLOP_PART_OF_GAME -> {
                Gambler gambler = table.getSeats()[table.getActiveSeat()];
                if (otherGamblersAreAllIn(table)) {
                    notifierTableDataService.notifyGambler(Attribute.ACT_OTHERS_ALL_IN_EVENT, gambler, null);
                } else {
                    notifierTableDataService.notifyGambler(Attribute.ACT_BETTED_POT_EVENT, gambler, null);
                }
            }
            case Attribute.FLOP_PART_OF_GAME,
                    Attribute.TURN_PART_OF_GAME,
                    Attribute.RIVER_PART_OF_GAME -> {
                Gambler gambler = table.getSeats()[table.getActiveSeat()];
                //if someone has betted
                if (table.getBiggestBet().compareTo(BigDecimal.ZERO) > 0) {
                    if (otherGamblersAreAllIn(table)) {
                        notifierTableDataService.notifyGambler(Attribute.ACT_OTHERS_ALL_IN_EVENT, gambler, null);
                    } else {
                        notifierTableDataService.notifyGambler(Attribute.ACT_BETTED_POT_EVENT, gambler, null);
                    }
                } else {
                    notifierTableDataService.notifyGambler(Attribute.ACT_NOT_BETTED_POT_EVENT, gambler, null);
                }
            }
        }
        notifierTableDataService.notifyALLGamblersOfRoom(table);
    }

    public void endRound(Table table) {
        potService.addTableBets(table);
        if (!table.isEmptyPots()) {
            int winnerSeat = findNextGambler(table, String.valueOf(0), true, false);
            String messageResultGame = potService.givenToWinner(table, table.getSeats()[winnerSeat]);
            Log log = Log.builder()
                    .setMessage(messageResultGame)
                    .setAction("")
                    .setSeat("")
                    .setNotification("")
                    .createLog();
            table.setLog(log);
            notifierTableDataService.notifyALLGamblersOfRoom(table);
        }
        //Sitting out the gamblers who don't have money
        for (int i = 0; i < table.getSeatsCount(); ++i) {
            if (table.getSeats()[i] != null
                    && table.getSeats()[i].getMoneyInPlay().compareTo(BigDecimal.ZERO) <= 0
                    && table.getSeats()[i].isSittingIn()) {
                table.getSeats()[i].sitOut();
                int gamblersSittingInCount = table.getGamblersSittingInCount() - 1;
                table.setGamblersSittingInCount(gamblersSittingInCount);
            }
        }
        //if there are not enough gamblers to continue the game, stop it
        if (table.getGamblersSittingInCount() < 2) {
            stopGame(table);
        } else {
            initializeRound(table, true);
        }
    }

    private void stopGame(Table table) {
        table.setPhaseGame(null);
        potService.reset(table);
        table.setActiveSeat(null);
        table.setBoard(new String[]{"", "", "", "", ""});
        table.setLastGamblerToAct(-1);
        removeAllCardsFromGame(table);
        table.setGameIsOn(false);
        notifierTableDataService.notifyALLGamblersOfRoomGameStop(table);
    }

    private void removeAllCardsFromGame(Table table) {
        for (Gambler gambler : table.getSeats()) {
            if (gambler != null && gambler.isSittingIn()) {
                gambler.setPublicCards(null);
                gambler.setPrivateCards(null);
                gambler.setHasCards(false);
            }
        }
    }
}
