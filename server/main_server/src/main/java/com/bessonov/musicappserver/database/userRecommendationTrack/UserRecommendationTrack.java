package com.bessonov.musicappserver.database.userRecommendationTrack;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_recommendation_track")
public class UserRecommendationTrack {
    @EmbeddedId
    private UserRecommendationTrackId id;

    @Column(name = "track_number_in_recommendation")
    private int trackNumberInRecommendation;

    public UserRecommendationTrackId getId() {
        return id;
    }

    public void setId(UserRecommendationTrackId id) {
        this.id = id;
    }

    public int getTrackNumberInRecommendation() {
        return trackNumberInRecommendation;
    }

    public void setTrackNumberInRecommendation(int trackNumberInRecommendation) {
        this.trackNumberInRecommendation = trackNumberInRecommendation;
    }

    @Override
    public String toString() {
        return "UserRecommendationTrack{" +
                "id=" + id +
                ", trackNumberInRecommendation=" + trackNumberInRecommendation +
                '}';
    }
}
