package com.epam.poker.service.game;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.model.dto.StatisticResultGame;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Log;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.constant.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.epam.poker.util.constant.EventMessage.*;

public class EventHandlerService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EventHandlerService instance = new EventHandlerService();
    private static final PotService potService = PotService.getInstance();
    private static final ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static final NotifierTableDataService notifierTableDataService = NotifierTableDataService.getInstance();
    private static final Lobby lobby = Lobby.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final PokerGameService pokerGameService = PokerGameService.getInstacne();

    private EventHandlerService() {
    }

    public static EventHandlerService getInstance() {
        return instance;
    }

    public void gamblerFolded(Table table, Gambler gambler) throws ServiceException {
        foldGambler(table, gambler);
        Log log = Log.builder()
                .setMessage(gambler.getName().concat(MSG_FOLDED))
                .setAction(ACTION_FOLD)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(NOTE_FOLD)
                .createLog();
        table.setLog(log);
        notifierTableDataService.notifyALLGamblersOfRoom(table);
        int gamblersInHandCount = table.getGamblersInHandCount() - 1;
        table.setGamblersInHandCount(gamblersInHandCount);
        potService.removeGambler(table, gambler);
        if (gamblersInHandCount <= 1) {
            potService.addTableBets(table);
            int winnerSeat = pokerGameService.findNextGambler(table, String.valueOf(table.getActiveSeat()), true, false);
            String winMessage = potService.givenToWinner(table, table.getSeats()[winnerSeat]);
            pokerGameService.endRound(table);
        } else {
            if (table.getLastGamblerToAct() == table.getActiveSeat()) {
                pokerGameService.endPhase(table);
            } else {
                pokerGameService.actionToNextGambler(table);
            }
        }
    }

    private void foldGambler(Table table, Gambler gambler) throws ServiceException {
        if (gambler != null) {
            BigDecimal loseMoney = table.getPots().get(0).getAmount()
                    .subtract(BigDecimal.valueOf(table.getGamblersInHandCount()));
            profilePlayerService.updateLostMoneyByLogin(gambler.getName(), loseMoney);
            StatisticResultGame statisticResultGame = lobby.findRoom(String.format(
                    Attribute.TABLE_WITH_HYPHEN, table.getId())).getStatisticResultGame();
            String twoCardsOfGambler = "";
            if (gambler.getPrivateCards() != null) {
                twoCardsOfGambler = Arrays.toString(gambler.getPrivateCards());
            }
            long userId = -1;
            try {
                userId = userService.findUserByLogin(gambler.getName()).getUserId();
            } catch (ServiceException e) {
                LOGGER.error("User not fount login: %s " + lobby + e);
            }
            if (userId == -1) {
                throw new ServiceException("Error found user by login");
            }
            GamePlayer gamePlayer = GamePlayer.builder()
                    .setLastAction(table.getPhaseGame())
                    .setTwoCards(twoCardsOfGambler)
                    .setCombinationsCards("")
                    .setUserId(userId)
                    .createGamePlayer();
            statisticResultGame.getGamePlayers().add(gamePlayer);
            gambler.setHasCards(false);
            gambler.setInHand(false);
            gambler.setPrivateCards(null);
            gambler.setPublicCards(null);
        }
    }

    public void gamblerChecked(Table table, Gambler gambler) throws ServiceException {
        Log log = Log.builder()
                .setMessage(gambler.getName().concat(MSG_CHECKED))
                .setAction(ACTION_CHECK)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(NOTE_CHECK)
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
                .setMessage(gambler.getName().concat(MSG_POSTED_SMALL_BLIND))
                .setAction(Attribute.BET)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(NOTE_POSTED_BLIND)
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
                .setMessage(gambler.getName().concat(MSG_POSTED_BIG_BLIND))
                .setAction(Attribute.BET)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(MSG_POSTED_BIG_BLIND)
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
                    .setMessage(gambler.getName() + MSG_SAT_OUT)
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

    public void gamblerLeft(Table table, Gambler gambler) throws ServiceException {
        Log log = Log.builder()
                .setMessage(gambler.getName() + MSG_LEFT)
                .setAction("")
                .setSeat("")
                .setNotification("")
                .createLog();
        table.setLog(log);
        int numberSeat = gambler.getNumberSeatOnTable();
        if (gambler.getSittingOnTable() > -1) {
            if (gambler.isSittingIn()) {
                gamblerSatOut(table, gambler, true);
            }
            gambler.leaveTable();
            if (gambler.getBalance().compareTo(BigDecimal.ZERO) >= 0) {
                userService.updateBalanceByLogin(gambler.getName(), gambler.getBalance());
            }
            //if there are not enough gamblers to continue the game
            if (table.getGamblersSittingInCount() < 2) {
                table.setDealerSeat(null);
            }
            table.deleteGamblerOfSeatByEntity(gambler);
            notifierTableDataService.notifyALLGamblersOfRoom(table);
            //if a gambler left a heads-up match and there are people waiting to gambler, start a new round
            if (table.getGamblersInHandCount() < 2) {
                pokerGameService.endRound(table);
            } //Else if the gambler was the last to act in this phase, end the phase
            else if (table.getLastGamblerToAct() == numberSeat
                    && table.getActiveSeat() == numberSeat) {
                pokerGameService.endPhase(table);
            }
        }
    }

    public void gamblerCalled(Table table, Gambler gambler) throws ServiceException {
        BigDecimal calledAmount = table.getBiggestBet().subtract(gambler.getBet());
        gambler.bet(calledAmount);
        Log log = Log.builder()
                .setMessage(gambler.getName().concat(MSG_CALLED))
                .setAction(ACTION_CALL)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(NOTE_CALL)
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

    public void gamblerBetted(Table table, Gambler gambler, BigDecimal amount) throws ServiceException {
        gambler.bet(amount);
        BigDecimal biggestBet = table.getBiggestBet().compareTo(gambler.getBet()) < 0
                ? gambler.getBet() : table.getBiggestBet();
        table.setBiggestBet(biggestBet);
        Log log = Log.builder()
                .setMessage(gambler.getName().concat(MSG_BETTED)
                        .concat(String.valueOf(amount)))
                .setAction(Attribute.BET)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(NOTE_BET.concat(String.valueOf(amount)))
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

    public void gamblerRaise(Table table, Gambler gambler, BigDecimal amount) throws ServiceException {
        gambler.raise(amount);
        BigDecimal oldBiggestBet = table.getBiggestBet();
        BigDecimal biggestBet = table.getBiggestBet().compareTo(gambler.getBet()) < 0
                ? gambler.getBet() : table.getBiggestBet();
        table.setBiggestBet(biggestBet);
        BigDecimal raiseAmount = table.getBiggestBet().subtract(oldBiggestBet);
        Log log = Log.builder()
                .setMessage(gambler.getName().concat(MSG_RAISE_TO)
                        .concat(String.valueOf(table.getBiggestBet())))
                .setAction(ACTION_RAISE)
                .setSeat(String.valueOf(table.getActiveSeat()))
                .setNotification(NOTE_RAISE.concat(String.valueOf(raiseAmount)))
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

    public void gamblerSatIn(Table table, Gambler gambler) {
        Log log = Log.builder()
                .setMessage(gambler.getName().concat(MSG_SET_IN))
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
            pokerGameService.initializeRound(table, false);
        }
    }

    public void gamblerSitOnTheTable(Table table, Gambler gambler, int numberSeat, BigDecimal bet) {
        table.getSeats()[numberSeat] = gambler;
        gambler.setSittingOnTable(table.getId());
        BigDecimal balanceGambler = gambler.getBalance().subtract(bet);
        gambler.setBalance(balanceGambler);
        gambler.setMoneyInPlay(bet);
        gambler.setNumberSeatOnTable(numberSeat);
        gamblerSatIn(table, gambler);
    }
}
