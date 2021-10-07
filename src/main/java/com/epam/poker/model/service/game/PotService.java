package com.epam.poker.model.service.game;

import com.epam.poker.model.entity.game.Gambler;
import com.epam.poker.model.entity.game.Pot;
import com.epam.poker.model.entity.game.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PotService {
    private static final PotService instance = new PotService();
    private static final Logger LOGGER = LogManager.getLogger();

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
                if (!(smallestBet.compareTo(BigDecimal.ZERO) > 0)) {
                    smallestBet = gambler.getBet();
                } else if (!gambler.getBet().equals(smallestBet)) {
                    allBetsAreaEqual = false;
                    if (gambler.getBet().compareTo(smallestBet) > 0) {
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

    public List<String> destributeToWinners(Table table, int firstGamblerToAct) {
        List<Pot> pots = table.getPots();
        int potsCount = pots.size();
        Gambler[] gamblers = table.getSeats();
        List<String> messages = new ArrayList<>();
        for (int i = potsCount - 1; i >= 0; --i) {
            List<Gambler> winners = new ArrayList<>();
            int bestRating = 0;
            int gamblersCount = gamblers.length;
            for (Gambler gambler : gamblers) {
                if (gambler != null && gambler.isInHand() && pots.get(i).getContributors()
                        .contains(gambler.getNumberSeatOnTable())) {
                    if (gambler.getEvaluateHand().getRating() > bestRating) {
                        bestRating = gambler.getEvaluateHand().getRating();
                        winners.add(gambler);
                    } else if (gambler.getEvaluateHand().getRating() == bestRating) {
                        winners.add(gambler);
                    }
                }
            }
            if (winners.size() == 1) {
                BigDecimal moneyInPlay = winners.get(0).getMoneyInPlay().add(pots.get(i).getAmount());
                winners.get(0).setMoneyInPlay(moneyInPlay);
                String htmlHand = "[";
                List<String> cards = winners.get(0).getEvaluateHand().getCards();
                for (String card : cards) {
                    htmlHand = htmlHand.concat(card + ", ");
                }
                htmlHand = htmlHand.concat("]");
                htmlHand = htmlHand.replace("/s/g", "&#9824;")
                        .replace("/c/g", "&#9827")
                        .replace("/h/g", "&#9829")
                        .replace("/d/g", "&#9830");
                messages.add(winners.get(0).getName() + " wins the pot (" + pots.get(i).getAmount()
                        + ") with" + winners.get(0).getEvaluateHand().getName() + " " + htmlHand);
            } else {
                int winnersCount = winners.size();
//                BigDecimal winnigs =
                //todo winners
            }
        }
        reset(table);
        return messages;
    }

    public String givenToWinner(Table table, Gambler gambler) {
        List<Pot> pots = table.getPots();
        int potsCount = pots.size();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = potsCount - 1; i >= 0; --i) {
            gambler.plusMoneyInTheGame(pots.get(i).getAmount());
            totalAmount = pots.get(i).getAmount();
        }
        reset(table);
        return gambler.getName() + " wins the pot (" + totalAmount + ")";
    }

    public void reset(Table table) {
        List<Pot> pots = new ArrayList<>(1);
        pots.add(new Pot());
        table.setPots(pots);
    }
}
