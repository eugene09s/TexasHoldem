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
}
