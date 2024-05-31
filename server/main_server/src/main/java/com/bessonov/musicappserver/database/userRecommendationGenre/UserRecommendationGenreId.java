package com.bessonov.musicappserver.database.userRecommendationGenre;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRecommendationGenreId implements Serializable {
    @Column(name = "user_recommendation_id")
    private int userRecommendationId;

    @Column(name = "genre_id")
    private int genreId;

    public int getUserRecommendationId() {
        return userRecommendationId;
    }

    public void setUserRecommendationId(int userRecommendationId) {
        this.userRecommendationId = userRecommendationId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecommendationGenreId that = (UserRecommendationGenreId) o;
        return userRecommendationId == that.userRecommendationId && genreId == that.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRecommendationId, genreId);
    }

    @Override
    public String toString() {
        return "UserRecommendationGenreId{" +
                "userRecommendationId=" + userRecommendationId +
                ", genreId=" + genreId +
                '}';
    }
}
