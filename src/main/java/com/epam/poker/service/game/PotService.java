package com.epam.poker.service.game;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.game.Game;
import com.epam.poker.model.database.game.GamePlayer;
import com.epam.poker.model.database.game.GameWinner;
import com.epam.poker.model.dto.StatisticResultGame;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Pot;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.StatisticOfGameService;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.service.database.impl.StatisticOfGameServiceImpl;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.constant.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class PotService {
    private static final PotService instance = new PotService();
    private static final String CODE_SPADES = "&#9824;";
    private static final String CODE_CLUBS = "&#9827;";
    private static final String CODE_HEARTS = "&#9829;";
    private static final String CODE_DIAMONDS = "&#9830;";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lobby lobby = Lobby.getInstance();
    private static final ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final StatisticOfGameService writeStatisticResultGame = StatisticOfGameServiceImpl.getInstance();

    private PotService() {
    }

    public static PotService getInstance() {
        return instance;
    }

    public void addTableBets(Table table) {
        int currentPot = table.getPots().size() - 1;
        BigDecimal smallestBet = BigDecimal.ZERO;
        boolean allBetsAreaEqual = true;
        Gambler[] gamblers = table.getSeats();
        for (Gambler gambler : gamblers) {
            if (gambler != null && gambler.getBet().compareTo(BigDecimal.ZERO) > 0) {
                if (smallestBet.equals(BigDecimal.ZERO)) {
                    smallestBet = gambler.getBet();
                } else if (!gambler.getBet().equals(smallestBet)) {
                    allBetsAreaEqual = false;
                    if (gambler.getBet().compareTo(smallestBet) < 0) {
                        smallestBet = gambler.getBet();
                    }
                }
            }
        }
        //if all the bets are eaual, then remove the bets of the gamblers and add
        //them to the pot as they are
        if (allBetsAreaEqual) {
            for (Gambler gambler : gamblers) {
                if (gambler != null && gambler.getBet().compareTo(BigDecimal.ZERO) > 0) {
                    table.getPotByIndex(currentPot).plusAmount(gambler.getBet());
                    gambler.setBet(BigDecimal.ZERO);
                    if (!table.getPots().get(currentPot).getContributors()
                            .contains(gambler.getNumberSeatOnTable())) {
                        table.getPots().get(currentPot).getContributors()
                                .add(gambler.getNumberSeatOnTable());
                    }
                }
            }
        } else {
            // If not all the bets are equal, remove from each player's bet the smallest bet
            // amount of the table, add these bets to the pot and then create a new empty pot
            // and recursively add the bets that remained, to the new pot
            for (Gambler gambler : gamblers) {
                if (gambler != null && gambler.getBet().compareTo(BigDecimal.ZERO) > 0) {
                    LOGGER.debug(currentPot + "  SmallBet: " + smallestBet);
                    table.getPotByIndex(currentPot).plusAmount(smallestBet);
                    gambler.setBet(gambler.getBet().subtract(smallestBet));
                    if (!table.getPots().get(currentPot).getContributors()
                            .contains(gambler.getNumberSeatOnTable())) {
                        table.getPots().get(currentPot).getContributors()
                                .add(gambler.getNumberSeatOnTable());
                    }
                }
            }
            table.getPots().add(new Pot());
            addTableBets(table);
        }
    }

    public void removeGambler(Table table, Gambler gambler) {
        List<Pot> pots = table.getPots();
        for (Pot pot : pots) {
            int placeInList = pot.getContributors().indexOf(gambler.getNumberSeatOnTable());
            if (placeInList >= 0) {
                pot.getContributors().remove(placeInList);
            }
        }
    }

    public void addGamblersBets(Table table, Gambler gambler) {
        List<Pot> pots = table.getPots();
        int currentPot = pots.size() - 1;
        pots.get(currentPot).plusAmount(gambler.getBet());
        gambler.setBet(BigDecimal.ZERO);
        if (!pots.get(currentPot).getContributors().contains(gambler.getNumberSeatOnTable())) {
            pots.get(currentPot).getContributors().add(gambler.getNumberSeatOnTable());
        }
    }

    public List<String> destributeToWinners(Table table, int firstGamblerToAct) throws ServiceException {
        List<Pot> pots = table.getPots();
        Gambler[] gamblers = table.getSeats();
        List<String> messages = new ArrayList<>();
        Set<Gambler> allGamblers = new HashSet<>();
        Set<Gambler> allWinnerGamblers = new HashSet<>();
        for (int i = pots.size() - 1; i >= 0; --i) {
            List<Gambler> winners = new ArrayList<>();
            List<Gambler> loseGamblers = new ArrayList<>();
            int bestRating = 0;
            for (Gambler gambler : gamblers) {
                if (gambler != null && gambler.isInHand()
                        && pots.get(i).getContributors()
                        .contains(gambler.getNumberSeatOnTable())) {
                    loseGamblers.add(gambler);
                    if (gambler.getEvaluateHand().getRating() > bestRating) {
                        bestRating = gambler.getEvaluateHand().getRating();
                        winners = new ArrayList<>();
                        winners.add(gambler);
                    } else if (gambler.getEvaluateHand().getRating() == bestRating) {
                        winners.add(gambler);
                    }
                }
            }
            allWinnerGamblers.addAll(winners);
            allGamblers.addAll(loseGamblers);
            handlerLoseGamblers(loseGamblers, winners, pots.get(i).getAmount());
            if (winners.size() == 1) {
                BigDecimal winMoney = pots.get(i).getAmount();
                try {
                    profilePlayerService.updateWinMoneyByLogin(winners.get(0).getName(), winMoney);
                    profilePlayerService.updateBestPrizeByLogin(winners.get(0).getName(), winMoney);
                } catch (ServiceException e) {
                    LOGGER.warn("Update win money profile player. " + e);
                }
                BigDecimal moneyInPlay = winners.get(0).getMoneyInPlay().add(winMoney);
                winners.get(0).setMoneyInPlay(moneyInPlay);
                messages.add(winners.get(0).getName() + " wins the pot ("+ pots.get(i).getAmount() +") with  "
                        + winners.get(0).getEvaluateHand().getName()
                        + handlerMessage(winners.get(0).getEvaluateHand().getCards()));
            } else {
                int winnersCount = winners.size();
                BigDecimal winnings = pots.get(i).getAmount().subtract(BigDecimal.valueOf(winnersCount));
                boolean isOddMoney = winnings.multiply(BigDecimal.valueOf(winnersCount)).equals(pots.get(i).getAmount());
                for (Gambler gambler : winners) {
                    BigDecimal gamblersWinnings = BigDecimal.ONE;
                    if (isOddMoney && gambler.getNumberSeatOnTable() == firstGamblerToAct) {
                        gamblersWinnings = winnings.add(BigDecimal.ONE);
                    } else {
                        gamblersWinnings = winnings;
                    }
                    try {
                        profilePlayerService.updateWinMoneyByLogin(gambler.getName(), gamblersWinnings);
                    } catch (ServiceException e) {
                        LOGGER.warn("Update win money profile player. " + e);
                    }
                    gambler.setMoneyInPlay(gambler.getMoneyInPlay().add(gamblersWinnings));
                    messages.add(gambler.getName() + " ties the pot (%d) with %s " + gamblersWinnings
                            + gambler.getEvaluateHand().getName()
                            + handlerMessage(gambler.getEvaluateHand().getCards()));
                }
            }
        }
        LOGGER.debug("Save statistice: Tabele-" + table +" All Gamblers- "+ allGamblers + " Winners: " + allWinnerGamblers);
        saveStatisticeGame(table, allGamblers, allWinnerGamblers);
        reset(table);
        return messages;
    }

    private void handlerLoseGamblers(List<Gambler> loseGamblers, List<Gambler> winGamblers, BigDecimal allMoney) {
        int gamlerCount = loseGamblers.size();
        for (Gambler gambler : winGamblers) {
            loseGamblers.remove(gambler);
        }
        BigDecimal loseMoney = allMoney.subtract(BigDecimal.valueOf(gamlerCount));
        for (Gambler gambler : loseGamblers) {
            try {
                profilePlayerService.updateLostMoneyByLogin(gambler.getName(), loseMoney);
            } catch (ServiceException e) {
                LOGGER.error("Update lose money profile player. " + e);
            }
        }
    }

    private void saveStatisticeGame(Table table, Set<Gambler> allGamblers, Set<Gambler> allWinnerGamblers) throws ServiceException {
        StatisticResultGame statisticResultGame = lobby.findRoom(String.format(
                Attribute.TABLE_WITH_HYPHEN, table.getId())).getStatisticResultGame();
        BigDecimal bankGame = BigDecimal.ZERO;
        for (Pot pot : table.getPots()) {
            bankGame = bankGame.add(pot.getAmount());
        }
        Game game = Game.builder()
                .setTitle(table.getName())
                .setDate(new Timestamp(System.currentTimeMillis()))
                .setBank(bankGame)
                .setFiveCards(Arrays.toString(table.getBoard())).createGame();
        statisticResultGame.setGame(game);
        for (Gambler gambler : allGamblers) {
            if (gambler.isInHand()) {
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
                        .setUserId(userId)
                        .setCombinationsCards(gambler.getEvaluateHand().getName())
                        .setTwoCards(Arrays.toString(gambler.getPrivateCards()))
                        .setLastAction(table.getPhaseGame())
                        .createGamePlayer();
                statisticResultGame.getGamePlayers().add(gamePlayer);
            }
        }

        for (Gambler gambler : allWinnerGamblers) {
            long userId = -1;
            try {
                userId = userService.findUserByLogin(gambler.getName()).getUserId();
            } catch (ServiceException e) {
                LOGGER.error("User not fount login: %s " + lobby + e);
            }
            if (userId == -1) {
                throw new ServiceException("Error found user by login");
            }
            GameWinner gameWinner = GameWinner.builder()
                    .setUserId(userId)
                    .createGameWinner();
            statisticResultGame.getGameWinners().add(gameWinner);
        }
        writeStatisticResultGame.pushData(statisticResultGame);
    }

    private String handlerMessage(List<String> cards) {
        return cards.toString().replace("s", CODE_SPADES)
                .replace("c", CODE_CLUBS)
                .replace("h", CODE_HEARTS)
                .replace("d", CODE_DIAMONDS);
    }

    public String givenToWinner(Table table, Gambler gambler) throws ServiceException {
        List<Pot> pots = table.getPots();
        int potsCount = pots.size();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = potsCount - 1; i >= 0; --i) {
            totalAmount = pots.get(i).getAmount();
            gambler.plusMoneyInTheGame(totalAmount);
        }
        profilePlayerService.updateWinMoneyByLogin(gambler.getName(), totalAmount);
        Set<Gambler> allGamblers = new HashSet<>();
        allGamblers.add(gambler);
        Set<Gambler> winGamblers = new HashSet<>();
        winGamblers.add(gambler);
        LOGGER.debug("Save statistice: Tabele-" + table + " All Gamblers-" + allGamblers + " Winners:" + winGamblers);
        saveStatisticeGame(table, allGamblers, winGamblers);
        reset(table);
        return gambler.getName().concat(" wins the pot (")
                .concat(String.valueOf(totalAmount)).concat(")");
    }

    public void reset(Table table) {
        lobby.findRoom(String.format(Attribute.TABLE_WITH_HYPHEN, table.getId()))
                .setStatisticResultGame(new StatisticResultGame());
        List<Pot> pots = new ArrayList<>(1);
        pots.add(new Pot());
        table.setPots(pots);
    }
}
