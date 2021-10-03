package com.epam.poker.game.entity;

import com.epam.poker.model.entity.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pot implements Entity {
    private BigDecimal amount;
    private List<Integer> contributors;

    public Pot(BigDecimal amount, List<Integer> contributors) {
        this.amount = amount;
        this.contributors = contributors;
    }

    public Pot() {
        amount = new BigDecimal(0);
        contributors = new ArrayList<>();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Pot setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public List<Integer> getContributors() {
        return contributors;
    }

    public Pot setContributors(List<Integer> contributors) {
        this.contributors = contributors;
        return this;
    }

    public void addGamblersBets(Gambler gambler) {
        this.amount = this.amount.add(gambler.getBet());
        gambler.setBet(BigDecimal.ZERO);
        if (!this.contributors.contains(gambler.getNumberSeatOnTable())) {
            this.contributors.add(gambler.getNumberSeatOnTable());
        }
    }

    public String[] destributeToWinners(Table table, int firstGamblerToAct) {
        //todo method
        return null;
    }

    public void addTableBets(List<Gambler> gamblers) {
        BigDecimal smallestBet = BigDecimal.ZERO;
        boolean allBetsAreaEqual = true;
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
                    this.amount = this.amount.add(gambler.getBet());
                    gambler.setBet(BigDecimal.ZERO);
                    if (!this.contributors.contains(gambler.getNumberSeatOnTable())) {
                        this.contributors.add(gambler.getNumberSeatOnTable());
                    }
                }
            }
        } else {
            // If not all the bets are equal, remove from each player's bet the smallest bet
            // amount of the table, add these bets to the pot and then create a new empty pot
            // and recursively add the bets that remained, to the new pot
            for (Gambler gambler : gamblers) {
                if (gambler != null && gambler.getBet().compareTo(BigDecimal.ZERO) > 0) {
                    this.amount = this.amount.add(smallestBet);
                    gambler.setBet(gambler.getBet().min(smallestBet));
                    if (!this.contributors.contains(gambler.getNumberSeatOnTable())) {
                        this.contributors.add(gambler.getNumberSeatOnTable());
                    }
                }
            }

            //may be add new Pot

            addTableBets(gamblers);
        }
    }

    public void removeGambler(Gambler gambler) {
        this.contributors.remove(gambler.getNumberSeatOnTable());
    }
}
