package com.epam.poker.game.logic;

import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Pot;
import com.epam.poker.game.entity.Table;
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
        pots.get(currentPot).plusAmount(gambler.getBet());//fixme
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
            List<Integer> winners = new ArrayList<>();
            int bestRating;
            int gamblersCount = gamblers.length;
            for (int j = 0; j < gamblersCount; j++) {
                if (gamblers[j] != null && gamblers[j].isInHand() && pots.get(i).getContributors()
                        .contains(gamblers[j].getNumberSeatOnTable())) {
//                    if (gamblers[j].getEvaluateHand()) {
//                        //todo method evaluate
//                    }
                }
            }
            if (winners.size() == 1) {
                gamblers[winners.get(0)].plusMoneyInTheGame(pots.get(i).getAmount());
                //todo evaluated hand
            }
        }
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

    private void reset(Table table) {
        List<Pot> pots = new ArrayList<>(1);
        pots.add(new Pot());
        table.setPots(pots);
    }
}
