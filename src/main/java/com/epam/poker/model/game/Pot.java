package com.epam.poker.model.game;

import com.epam.poker.model.Entity;

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

    public boolean isEmpty() {
        if (amount.equals(BigDecimal.ZERO) && contributors.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<Integer> getContributors() {
        return contributors;
    }

    public Pot setContributors(List<Integer> contributors) {
        this.contributors = contributors;
        return this;
    }

    public void plusAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void removeGambler(Gambler gambler) {
        this.contributors.remove(gambler.getNumberSeatOnTable());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pot pot = (Pot) o;

        if (amount != null ? !amount.equals(pot.amount) : pot.amount != null) return false;
        return contributors != null ? contributors.equals(pot.contributors) : pot.contributors == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (contributors != null ? contributors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pot{");
        sb.append("amount=").append(amount);
        sb.append(", contributors=").append(contributors);
        sb.append('}');
        return sb.toString();
    }
}
