package com.epam.poker.game.logic;

import com.epam.poker.game.entity.Deck;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Log;
import com.epam.poker.game.entity.Table;
import com.epam.poker.util.constant.Attribute;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;

public class PokerGameService {
    private static final PokerGameService instance = new PokerGameService();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TWO_GAMBLERS = 2;
    private static final int TWO_CARDS = 2;
    private static final int ZERO_MONEY = 0;
    private static final byte DO_NOT_CHANGE_DEALER = 1;
    private static final String SMALL_BLIND = "smallBlind";
    private static NotifierTableDataService notifierTableDataService = NotifierTableDataService.getInstance();

    private PokerGameService() {
    }

    public static PokerGameService getInstacne() {
        return instance;
    }

    public void playerSatIn(Table table, Gambler gambler) {
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
            initializeRound(table, DO_NOT_CHANGE_DEALER);
        }
    }

    private void initializeRound(Table table, byte changeDealer) {
        if (table.getGamblersSittingInCount() > 1) {
            table.setGameIsOn(true);
            table.setBoard(new String[]{"", "", "", "", ""});
            table.getDeck().shuffle();
            table.setHeadsUp(table.getGamblersSeatedCount() == TWO_GAMBLERS);
            table.setGamblersInHandCount(0);
            int seatsCount = table.getSeatsCount();
            for (int i = 0; i < seatsCount; ++i) {
                //if a player is sitting on the current seat
                if (table.getSeats()[i] != null
                        && table.getSeats()[i].isSittingIn()) {
                    BigDecimal moneyGambler = table.getSeats()[i].getMoneyInPlay();
                    if (!(moneyGambler.compareTo(BigDecimal.ONE) >= ZERO_MONEY)) { //fixme perhapse mark moneygambler
//                        table.getSeats().get(); //also mark
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
            } else if (changeDealer > -1
                    || !table.getSeats()[table.getDealerSeat()].isSittingIn()) {
                //if the dealer should be changed because the game will start with a new player
                //or if the old dealer is sitting out, give the dealer button to the next player
                table.setDealerSeat(findNextPlayer(table));
            }
            initializeSmallBlind(table);
        }
    }

    private Integer findPreviousGambler(Table table) {
        //todo method
        return null;
    }

    public void initializeSmallBlind(Table table) {
        table.setPhaseGame(SMALL_BLIND);
        if (table.isHeadsUp()) {
            table.setActiveSeat(table.getDealerSeat());
        } else {
            table.setActiveSeat(findNextPlayer(table));
        }
        table.setLastGamblerToAct("10");
        Gambler gambler = table.getSeats()[table.getActiveSeat()];
        if (gambler != null) {
            notifierTableDataService.notifyGambler(Attribute.POST_SMALL_BLIND_EVENT, gambler);
        } else {
            LOGGER.error("Gambler don't found!");
        }
        notifierTableDataService.notifyALLGamblersOfRoom(table);
    }

    private void prepareForNewRound(Gambler gambler) {
        gambler.setHasCards(false);
        gambler.setPrivateCards(new String[TWO_CARDS]);
        gambler.setPublicCards(new String[TWO_CARDS]);
        gambler.setBet(BigDecimal.ZERO);
        gambler.setInHand(true);
    }

    public void gamblerLeft(Table table, Gambler gambler) {
        Log log = Log.builder()
                .setMessage(gambler.getName() + " left")
                .setAction("")
                .setSeat("")
                .setNotification("")
                .createLog();
        String nextAxtion = "";
        if (gambler.isSittingIn()) {
            playerSatOut(table, gambler, true);
        }
        gambler.leaveTable();
        int gamblersSeatedCount = table.getGamblersSeatedCount() - 1;
        table.setGamblersSeatedCount(gamblersSeatedCount);
        //if there are not enough gamblers to continue the game
        if (table.getGamblersSeatedCount() < 2) {
            table.setDealerSeat(null);
        }
        table.deleteGamblerOfSeatByEntity(gambler);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        //if a gambler left a heads-up match and there are people waiting to gambler, start a new round
        if (table.getGamblersInHandCount() < 2) {
            endRound();
        } //Else if the gambler was the last to act in this phase, end the phase
        else if (table.getLastGamblerToAct().equals(String.valueOf(gambler.getNumberSeatOnTable()))
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
                table.setBoard((String[]) deck.pullSomeCardsFromDeck(3).toArray());
                break;
            }
            case "flop" -> {
                table.setPhaseGame("turn");
                String[] board = table.getBoard();
                Deck deck = table.getDeck();
                board[3] = deck.pullSomeCardsFromDeck(1).get(0);
                break;
            }
            case "turn" -> {
                table.setPhaseGame("river");
                String[] board = table.getBoard();
                Deck deck = table.getDeck();
                board[4] = deck.pullSomeCardsFromDeck(1).get(0);
                break;
            }
        }
        table.getPot().addTableBets(Arrays.stream(table.getSeats()).toList());
        table.setBiggestBet(BigDecimal.ZERO);

        table.setActiveSeat(findNextPlayer(table));
        table.setLastGamblerToAct(String.valueOf(
                findPreviousGambler(table, table.getActiveSeat().toString(), true, false)));
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        //if all other gamblers are all in, there should be on actions. Move to the next round.
        if (otherGamblersAreAllIn()) {
//            setTimeout(
//                    endPhase();
//            ) 1000
        } else {
            Gambler[] gamblers = table.getSeats();
            Gambler gambler = gamblers[table.getActiveSeat()];
            notifierTableDataService.notifyGambler("actNotBettedpot", gambler);
        }
    }

    private void showdown(Table table) {
        //todo method
    }

    private void playerSatOut(Table table, Gambler gambler, boolean playerLeft) {
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
            table.getPot().addGamblersBets(gambler);
        }
        table.getPot().removeGambler(gambler);
        String nextAction = "";
        int playersSittingInCount = table.getGamblersSittingInCount() - 1;
        table.setGamblersSittingInCount(playersSittingInCount);
        if (gambler.isInHand()) {
            gambler.sitOut();
            int playersInHandCount = table.getGamblersInHandCount() - 1;
            table.setGamblersInHandCount(playersInHandCount);
            if (playersInHandCount < 2) {
                if (!playerLeft) {
                    endRound();
                }
            } else {
                //if the gamber was not the last gambler to act but they were the gambler who should act in this round
                if (table.getActiveSeat().equals(gambler.getNumberSeatOnTable())
                        && table.getLastGamblerToAct() != String.valueOf(gambler.getNumberSeatOnTable())) {
                    actionToNextGambler();
                } //if the gambler was the last gambler to act and they left when they had to act
                else if (table.getLastGamblerToAct().equals(String.valueOf(gambler.getNumberSeatOnTable()))
                        && table.getActiveSeat().equals(gambler.getNumberSeatOnTable())) {
                    if (!playerLeft) {
                        endRound();
                    }
                }
                //if the gambler was the last to act but not the gambler who should act
                else if (table.getLastGamblerToAct().equals(String.valueOf(gambler.getNumberSeatOnTable()))) {
                    int lastGamblerToAct = findPreviousGambler(table, table.getLastGamblerToAct(), true, false);
                    table.setLastGamblerToAct(String.valueOf(lastGamblerToAct));
                }
            }
        } else {
            gambler.sitOut();
        }
        notifierTableDataService.notifyALLGamblersOfRoom(table);
    }

    private int findPreviousGambler(Table table, String lineOffset, boolean checkInHand, boolean checkMoneyInGame) {
        int offset = Integer.parseInt(lineOffset);
        if (checkInHand || checkMoneyInGame) {
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

    private void actionToNextGambler() {
        //todo method
    }

    private void endRound() {
        //todo method
    }
}
