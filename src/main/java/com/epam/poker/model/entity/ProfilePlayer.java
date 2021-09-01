package com.epam.poker.model.entity;

import com.epam.poker.model.Entity;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.StringJoiner;

public class ProfilePlayer implements Entity {
    private long userId;
    private BigDecimal ranking;
    private BigDecimal bestPrize;
    private String award;
    private Blob photo;
    private String aboutYourselt;

    public ProfilePlayer(long userId, BigDecimal ranking, BigDecimal bestPrize,
                         String award, Blob photo, String aboutYourselt) {
        this.userId = userId;
        this.ranking = ranking;
        this.bestPrize = bestPrize;
        this.award = award;
        this.photo = photo;
        this.aboutYourselt = aboutYourselt;
    }

    private ProfilePlayer() {
    }

    public long getUserId() {
        return userId;
    }

    public ProfilePlayer setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public BigDecimal getRanking() {
        return ranking;
    }

    public ProfilePlayer setRanking(BigDecimal ranking) {
        this.ranking = ranking;
        return this;
    }

    public BigDecimal getBestPrize() {
        return bestPrize;
    }

    public ProfilePlayer setBestPrize(BigDecimal bestPrize) {
        this.bestPrize = bestPrize;
        return this;
    }

    public String getAward() {
        return award;
    }

    public ProfilePlayer setAward(String award) {
        this.award = award;
        return this;
    }

    public Blob getPhoto() {
        return photo;
    }

    public ProfilePlayer setPhoto(Blob photo) {
        this.photo = photo;
        return this;
    }

    public String getAboutYourselt() {
        return aboutYourselt;
    }

    public ProfilePlayer setAboutYourselt(String aboutYourselt) {
        this.aboutYourselt = aboutYourselt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePlayer that = (ProfilePlayer) o;

        if (userId != that.userId) return false;
        if (ranking != null ? !ranking.equals(that.ranking) : that.ranking != null) return false;
        if (bestPrize != null ? !bestPrize.equals(that.bestPrize) : that.bestPrize != null) return false;
        if (award != null ? !award.equals(that.award) : that.award != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        return aboutYourselt != null ? aboutYourselt.equals(that.aboutYourselt) : that.aboutYourselt == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (ranking != null ? ranking.hashCode() : 0);
        result = 31 * result + (bestPrize != null ? bestPrize.hashCode() : 0);
        result = 31 * result + (award != null ? award.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (aboutYourselt != null ? aboutYourselt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProfilePlayer.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("ranking=" + ranking)
                .add("bestPrize=" + bestPrize)
                .add("award='" + award + "'")
                .add("photo=" + photo)
                .add("aboutYourselt='" + aboutYourselt + "'")
                .toString();
    }

    public static ProfilePlayerBuilder builder() {
        return new ProfilePlayerBuilder();
    }

    public static class ProfilePlayerBuilder {
        private ProfilePlayer profilePlayer;

        public ProfilePlayerBuilder() {
            profilePlayer = new ProfilePlayer();
        }

        public ProfilePlayerBuilder setUserId(long userId) {
            profilePlayer.setUserId(userId);
            return this;
        }

        public ProfilePlayerBuilder setRanking(BigDecimal ranking) {
            profilePlayer.setRanking(ranking);
            return this;
        }

        public ProfilePlayerBuilder setBestPrize(BigDecimal bestPrize) {
            profilePlayer.setBestPrize(bestPrize);
            return this;
        }

        public ProfilePlayerBuilder setAward(String award) {
            profilePlayer.setAward(award);
            return this;
        }

        public ProfilePlayerBuilder setPhoto(Blob blob) {
            profilePlayer.setPhoto(blob);
            return this;
        }

        public ProfilePlayerBuilder setAboutYourself(String lines) {
            profilePlayer.setAboutYourselt(lines);
            return this;
        }

        public ProfilePlayer createRatingPlayer() {
            return profilePlayer;
        }
    }
}
