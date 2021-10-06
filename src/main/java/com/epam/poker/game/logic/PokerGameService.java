package com.epam.poker.game.logic;

import com.epam.poker.game.entity.*;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PokerGameService {
    private static final PokerGameService instance = new PokerGameService();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TWO_GAMBLERS = 2;
    private static final int TWO_CARDS = 2;
    private static final int ZERO_MONEY = 0;
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

    public void gamblerFolded(Table table, Gambler gambler) {
        foldGambler(gambler);
        Log log = Log.builder()
                .setMessage(gambler.getName() + " folded")
                .setAction("fold")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Fold")
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        int gamblersInHandCount = table.getGamblersInHandCount() - 1;
        table.setGamblersInHandCount(gamblersInHandCount);
        potService.removeGambler(table, gambler);
        if (gamblersInHandCount <= 1) {
            potService.addTableBets(table);
            int winnerSeat = findNextGambler(table, String.valueOf(table.getActiveSeat()), true, false);
            potService.givenToWinner(table, table.getSeats()[winnerSeat]);
            endRound(table);
        } else {
            if (table.getLastGamblerToAct() == table.getActiveSeat()) {
                endPhase(table);
            } else {
                actionToNextGambler(table);
            }
        }
    }

    private void foldGambler(Gambler gambler) {
        gambler.setHasCards(false);
        gambler.setInHand(false);
        gambler.setPrivateCards(null);
        gambler.setPublicCards(null);
    }

    public void gamblerChecked(Table table, Gambler gambler) {
        Log log = Log.builder()
                .setMessage(gambler.getName() + " checked")
                .setAction("check")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Check")
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        if (table.getLastGamblerToAct() == table.getActiveSeat()) {
            endPhase(table);
        } else {
            actionToNextGambler(table);
        }
    }

    public void gamblerPostedSmallBlind(Table table, Gambler gambler) {
        BigDecimal bet = gambler.getMoneyInPlay().compareTo(table.getSmallBlind()) >= 0 ?
                table.getSmallBlind() : gambler.getMoneyInPlay();
        table.getSeats()[table.getActiveSeat()].bet(bet);
        Log log = Log.builder()
                .setMessage(gambler.getName() + " posted the small blind")
                .setAction("bet")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Posted blind")
                .createLog();
        table.setLog(log);
        BigDecimal biggestBet = table.getBiggestBet().compareTo(bet) < 0 ? bet : table.getBiggestBet();
        table.setBiggestBet(biggestBet);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        initializeBigBlind(table);
    }

    public void gamblerPostedBigBlind(Table table, Gambler gambler) {
        BigDecimal bet = gambler.getMoneyInPlay().compareTo(table.getSmallBlind()) >= 0 ?
                table.getBigBlind() : gambler.getMoneyInPlay();
        table.getSeats()[table.getActiveSeat()].bet(bet);
        Log log = Log.builder()
                .setMessage(gambler.getName() + " posted the big blind")
                .setAction("bet")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Posted blind")
                .createLog();
        table.setLog(log);
        BigDecimal biggestBet = table.getBiggestBet().compareTo(bet) < 0 ? bet : table.getBiggestBet();
        table.setBiggestBet(biggestBet);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        initializePreflop(table);
    }

    public void gamblerCalled(Table table, Gambler gambler) {
        BigDecimal calledAmount = table.getBiggestBet().subtract(gambler.getBet());
        gambler.bet(calledAmount);
        Log log = Log.builder()
                .setMessage(gambler.getName() + " called")
                .setAction("call")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Call")
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        if (table.getLastGamblerToAct() == table.getActiveSeat()
                || otherGamblersAreAllIn(table)) {
            endPhase(table);
        } else {
            actionToNextGambler(table);
        }
    }

    public void gamblerBetted(Table table, Gambler gambler, BigDecimal amount) {
        gambler.bet(amount);
        BigDecimal biggestBet = table.getBiggestBet().compareTo(gambler.getBet()) < 0
                ? gambler.getBet() : table.getBiggestBet();
        table.setBiggestBet(biggestBet);
        Log log = Log.builder()
                .setMessage(gambler.getName() + " betted " + amount)
                .setAction("bet")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Bet " + amount)
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        int previousGamblerSeat = findPreviousGambler(table,
                table.getActiveSeat(), true, false);
        if (previousGamblerSeat == table.getActiveSeat()) {
            endPhase(table);
        } else {
            table.setLastGamblerToAct(previousGamblerSeat);
            actionToNextGambler(table);
        }
    }

    public void initializePreflop(Table table) {
        table.setPhaseGame(Attribute.PRE_FLOP_PART_OF_GAME);
        int currentGambler = table.getActiveSeat();
        table.setLastGamblerToAct(table.getActiveSeat());
        for (int i = 0; i < table.getGamblersInHandCount(); ++i) {
            Gambler gambler = table.getSeats()[i];
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

    private void initializeBigBlind(Table table) {
        table.setPhaseGame(BIG_BLIND);
        actionToNextGambler(table);
    }

    public void gamblerSatIn(Table table, Gambler gambler) {
        Log log = Log.builder()
                .setMessage(gambler.getName() + " sat in")
                .setAction("")
                .setSeat("")
                .setNotification("")
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        gambler.setSittingIn(true);
        int playersSittingInCount = table.getGamblersSittingInCount() + 1;
        table.setGamblersSittingInCount(playersSittingInCount);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        if (!table.isGameIsOn() && playersSittingInCount > 1) {
            initializeRound(table, false);
        }
    }

    private void initializeRound(Table table, boolean changeDealer) {
        if (table.getGamblersSittingInCount() > 1) {
            table.setGameIsOn(true);
            table.setBoard(new String[]{"", "", "", "", ""});
            table.getDeck().shuffle();
            table.setHeadsUp(table.getGamblersSittingInCount() == TWO_GAMBLERS);
            table.setGamblersInHandCount(0);
            int seatsCount = table.getSeatsCount();
            for (int i = 0; i < seatsCount; ++i) {
                //if a player is sitting on the current seat
                if (table.getSeats()[i] != null
                        && table.getSeats()[i].isSittingIn()) {
                    BigDecimal moneyGambler = table.getSeats()[i].getMoneyInPlay();
                    if (moneyGambler.equals(BigDecimal.ZERO)) {
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

    public void gamblerLeft(Table table, Gambler gambler) {
        Log log = Log.builder()
                .setMessage(gambler.getName() + " left")
                .setAction("")
                .setSeat("")
                .setNotification("")
                .createLog();
        table.setLog(log);
        if (gambler.isSittingIn()) {
            gamblerSatOut(table, gambler, true);
        }
        gambler.leaveTable();
        int gamblersSeatedCount = table.getGamblersSittingInCount() - 1;
        table.setGamblersSittingInCount(gamblersSeatedCount);
        //if there are not enough gamblers to continue the game
        if (table.getGamblersSittingInCount() < 2) {
            table.setDealerSeat(null);
        }
        table.deleteGamblerOfSeatByEntity(gambler);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        //if a gambler left a heads-up match and there are people waiting to gambler, start a new round
        if (table.getGamblersInHandCount() < 2) {
            endRound(table);
        } //Else if the gambler was the last to act in this phase, end the phase
        else if (table.getLastGamblerToAct() == gambler.getNumberSeatOnTable()
                && table.getActiveSeat() == gambler.getNumberSeatOnTable()) {
            endPhase(table);
        }
    }

    private void endPhase(Table table) {
        switch (table.getPhaseGame()) {
            case "preflop", "flop", "turn" -> initializeNextPhase(table);
            case "river" -> showdown(table);
        }
    }

    private void initializeNextPhase(Table table) {
        switch (table.getPhaseGame()) {
            case "preflop" -> {
                table.setPhaseGame("flop");
                Deck deck = table.getDeck();
                table.setBoard(deck.pullSomeCardsFromDeck(3).toArray(new String[0]));
            }
            case "flop" -> {
                table.setPhaseGame("turn");
                Deck deck = table.getDeck();
                table.getBoard()[3] =  deck.pullSomeCardsFromDeck(1).get(0);;
            }
            case "turn" -> {
                table.setPhaseGame("river");
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
                LOGGER.error("Sleep 1000 err:" + e);
            }
            endPhase(table);
        } else {
            Gambler[] gamblers = table.getSeats();
            Gambler gambler = gamblers[table.getActiveSeat()];
            notifierTableDataService.notifyGambler(Attribute.ACT_NOT_BETTED_POT_EVENT, gambler, null);
        }
    }

    private int findNextGambler(Table table, String lineOffset, boolean checkInHand, boolean checkMoneyInGame) {
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

    private boolean otherGamblersAreAllIn(Table table) {
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
            evaluateHandService.execute(table, table.getSeats()[i]);
            if (table.getSeats()[currentGambler].getEvaluateHand().getRating() > bestHandRating) {
                table.getSeats()[currentGambler].setPublicCards(
                        table.getSeats()[currentGambler].getPrivateCards());
            }
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("Sleep err:" + e);
        }
        endRound(table);
    }

    public void gamblerSatOut(Table table, Gambler gambler, boolean playerLeft) {
        if (playerLeft) {
            Log log = Log.builder()
                    .setMessage(gambler.getName() + " sat out")
                    .setAction("")
                    .setSeat("")
                    .setNotification("")
                    .createLog();
            table.setLog(log);
            notifierTableDataService.notifyALLGamblersOfRoom(table);
        }
        if (gambler.getBet().compareTo(BigDecimal.ZERO) > 0) {
            potService.addGamblersBets(table, gambler);
        }
        potService.removeGambler(table, gambler);
        String nextAction = "";
        int playersSittingInCount = table.getGamblersSittingInCount() - 1;
        table.setGamblersSittingInCount(playersSittingInCount);
        if (gambler.isInHand()) {
            gambler.sitOut();
            int playersInHandCount = table.getGamblersInHandCount() - 1;
            table.setGamblersInHandCount(playersInHandCount);
            if (playersInHandCount < 2) {
                if (!playerLeft) {
                    endRound(table);
                }
            } else {
                //if the gamber was not the last gambler to act but they were the gambler who should act in this round
                if (table.getActiveSeat() == gambler.getNumberSeatOnTable()
                        && table.getLastGamblerToAct() != gambler.getNumberSeatOnTable()) {
                    actionToNextGambler(table);
                } //if the gambler was the last gambler to act and they left when they had to act
                else if (table.getLastGamblerToAct() == gambler.getNumberSeatOnTable()
                        && table.getActiveSeat().equals(gambler.getNumberSeatOnTable())) {
                    if (!playerLeft) {
                        endRound(table);
                    }
                }
                //if the gambler was the last to act but not the gambler who should act
                else if (table.getLastGamblerToAct() == gambler.getNumberSeatOnTable()) {
                    int lastGamblerToAct = findPreviousGambler(table, table.getLastGamblerToAct(), true, false);
                    table.setLastGamblerToAct(lastGamblerToAct);
                }
            }
        } else {
            gambler.sitOut();
        }
        notifierTableDataService.notifyALLGamblersOfRoom(table);
    }

    private int findPreviousGambler(Table table, int offset, boolean checkInHand, boolean checkMoneyInGame) {
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

    private void actionToNextGambler(Table table) {
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

    private void endRound(Table table) {
        potService.addTableBets(table);
        if (!table.getPots().isEmpty()) {
            int winnerSeat = findNextGambler(table, String.valueOf(0), true, false);
            String messageResultGame = potService.givenToWinner(table, table.getSeats()[winnerSeat]);
            //todo should be send mesage result
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
        table.setPots(new ArrayList<>(1));
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
