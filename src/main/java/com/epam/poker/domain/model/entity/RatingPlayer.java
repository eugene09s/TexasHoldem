package com.epam.poker.domain.model.entity;

import com.epam.poker.domain.model.Entity;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class RatingPlayer implements Entity {
    private long userId;
    private BigDecimal ranking;
    private BigDecimal bestPrize;
    private String award;

    public RatingPlayer(long userId, BigDecimal ranking, BigDecimal bestPrize, String award) {
        this.userId = userId;
        this.ranking = ranking;
        this.bestPrize = bestPrize;
        this.award = award;
    }

    private RatingPlayer() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getRanking() {
        return ranking;
    }

    public void setRanking(BigDecimal ranking) {
        this.ranking = ranking;
    }

    public BigDecimal getBestPrize() {
        return bestPrize;
    }

    public void setBestPrize(BigDecimal bestPrize) {
        this.bestPrize = bestPrize;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingPlayer that = (RatingPlayer) o;

        if (userId != that.userId) return false;
        if (ranking != null ? !ranking.equals(that.ranking) : that.ranking != null) return false;
        if (bestPrize != null ? !bestPrize.equals(that.bestPrize) : that.bestPrize != null) return false;
        return award != null ? award.equals(that.award) : that.award == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (ranking != null ? ranking.hashCode() : 0);
        result = 31 * result + (bestPrize != null ? bestPrize.hashCode() : 0);
        result = 31 * result + (award != null ? award.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RatingPlayer.class.getSimpleName() + "[", "]")
                .add("UserId=" + userId)
                .add("ranking=" + ranking)
                .add("bestPrize=" + bestPrize)
                .add("award='" + award + "'")
                .toString();
    }

    public static RatingPlayerBuilder builder() {
        return new RatingPlayerBuilder();
    }

    public static class RatingPlayerBuilder {
        private RatingPlayer ratingPlayer;

        public RatingPlayerBuilder() {
            ratingPlayer = new RatingPlayer();
        }

        public RatingPlayerBuilder setUserId(long userId) {
            ratingPlayer.setUserId(userId);
            return this;
        }

        public RatingPlayerBuilder setRanking(BigDecimal ranking) {
            ratingPlayer.setRanking(ranking);
            return this;
        }

        public RatingPlayerBuilder setBestPrize(BigDecimal bestPrize) {
            ratingPlayer.setBestPrize(bestPrize);
            return this;
        }

        public RatingPlayerBuilder setAward(String award) {
            ratingPlayer.setAward(award);
            return this;
        }

        public RatingPlayer createRatingPlayer() {
            return new RatingPlayer();
        }
    }
}
