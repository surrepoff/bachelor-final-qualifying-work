package com.bessonov.musicappserver.database.userRecommendationGenre;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_recommendation_genre")
public class UserRecommendationGenre {
    @EmbeddedId
    private UserRecommendationGenreId id;

    public UserRecommendationGenreId getId() {
        return id;
    }

    public void setId(UserRecommendationGenreId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserRecommendationGenre{" +
                "id=" + id +
                '}';
    }
}
