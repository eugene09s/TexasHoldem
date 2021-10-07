package com.epam.poker.game.logic;

import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Log;
import com.epam.poker.game.entity.Table;

import java.math.BigDecimal;

public class EventHandlerService {
    private static final EventHandlerService instance = new EventHandlerService();
    private static PotService potService = PotService.getInstance();
    private static NotifierTableDataService notifierTableDataService = NotifierTableDataService.getInstance();
    private static PokerGameService pokerGameService = PokerGameService.getInstacne();

    private EventHandlerService() {
    }

    public static EventHandlerService getInstance() {
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
            int winnerSeat = pokerGameService.findNextGambler(table, String.valueOf(table.getActiveSeat()), true, false);
            String winMessage = potService.givenToWinner(table, table.getSeats()[winnerSeat]);//todo output win message
            pokerGameService.endRound(table);
        } else {
            if (table.getLastGamblerToAct() == table.getActiveSeat()) {
                pokerGameService.endPhase(table);
            } else {
                pokerGameService.actionToNextGambler(table);
            }
        }
    }

    private void foldGambler(Gambler gambler) {
        if (gambler != null) {
            gambler.setHasCards(false);
            gambler.setInHand(false);
            gambler.setPrivateCards(null);
            gambler.setPublicCards(null);
        }
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
            pokerGameService.endPhase(table);
        } else {
            pokerGameService.actionToNextGambler(table);
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
        pokerGameService.initializeBigBlind(table);
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
        pokerGameService.initializePreflop(table);
    }

    public void gamblerSatOut(Table table, Gambler gambler, boolean gamblerLeft) {
        if (!gamblerLeft) {
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
        int playersSittingInCount = table.getGamblersSittingInCount() - 1;
        table.setGamblersSittingInCount(playersSittingInCount);
        if (gambler.isInHand()) {
            gambler.sitOut();
            int playersInHandCount = table.getGamblersInHandCount() - 1;
            table.setGamblersInHandCount(playersInHandCount);
            if (playersInHandCount < 2) {
                if (!gamblerLeft) {
                    pokerGameService.endRound(table);
                }
            } else {
                //if the gamber was not the last gambler to act but they were the gambler who should act in this round
                if (table.getActiveSeat() == gambler.getNumberSeatOnTable()
                        && table.getLastGamblerToAct() != gambler.getNumberSeatOnTable()) {
                    pokerGameService.actionToNextGambler(table);
                } //if the gambler was the last gambler to act and they left when they had to act
                else if (table.getLastGamblerToAct() == gambler.getNumberSeatOnTable()
                        && table.getActiveSeat().equals(gambler.getNumberSeatOnTable())) {
                    if (!gamblerLeft) {
                        pokerGameService.endRound(table);
                    }
                }
                //if the gambler was the last to act but not the gambler who should act
                else if (table.getLastGamblerToAct() == gambler.getNumberSeatOnTable()) {
                    int lastGamblerToAct = pokerGameService.findPreviousGambler(table, table.getLastGamblerToAct(), true, false);
                    table.setLastGamblerToAct(lastGamblerToAct);
                }
            }
        } else {
            gambler.sitOut();
        }
        notifierTableDataService.notifyALLGamblersOfRoom(table);
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
//        int gamblersSeatedCount = table.getGamblersSittingInCount() - 1;
//        table.setGamblersSittingInCount(gamblersSeatedCount);
        //todo seated count -1
        //if there are not enough gamblers to continue the game
        if (table.getGamblersSittingInCount() < 2) {//seated count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            table.setDealerSeat(null);
        }
        table.deleteGamblerOfSeatByEntity(gambler);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        //if a gambler left a heads-up match and there are people waiting to gambler, start a new round
        if (table.getGamblersInHandCount() < 2) {
            pokerGameService.endRound(table);
        } //Else if the gambler was the last to act in this phase, end the phase
        else if (table.getLastGamblerToAct() == gambler.getNumberSeatOnTable()
                && table.getActiveSeat() == gambler.getNumberSeatOnTable()) {
            pokerGameService.endPhase(table);
        }
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
                || pokerGameService.otherGamblersAreAllIn(table)) {
            pokerGameService.endPhase(table);
        } else {
            pokerGameService.actionToNextGambler(table);
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
        int previousGamblerSeat = pokerGameService.findPreviousGambler(table,
                table.getActiveSeat(), true, false);
        if (previousGamblerSeat == table.getActiveSeat()) {
            pokerGameService.endPhase(table);
        } else {
            table.setLastGamblerToAct(previousGamblerSeat);
            pokerGameService.actionToNextGambler(table);
        }
    }

    public void gamblerRaise(Table table, Gambler gambler, BigDecimal amount) {
        gambler.raise(amount);
        BigDecimal oldBiggestBet = table.getBiggestBet();
        BigDecimal biggestBet = table.getBiggestBet().compareTo(gambler.getBet()) < 0
                ? gambler.getBet() : table.getBiggestBet();
        table.setBiggestBet(biggestBet);
        BigDecimal raiseAmount = table.getBiggestBet().subtract(oldBiggestBet);
        Log log = Log.builder()
                .setMessage(gambler.getName() + " raise to " + table.getBiggestBet())
                .setAction("raise")
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification("Raise  " + raiseAmount)
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        int previousGamblerSeat = pokerGameService.findPreviousGambler(table,
                table.getActiveSeat(), true, false);
        if (previousGamblerSeat == table.getActiveSeat()) {
            pokerGameService.endPhase(table);
        } else {
            table.setLastGamblerToAct(previousGamblerSeat);
            pokerGameService.actionToNextGambler(table);
        }
    }
}
