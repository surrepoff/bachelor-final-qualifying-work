package com.bessonov.musicappserver.database.userRecommendationTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRecommendationTrackId implements Serializable {
    @Column(name = "user_recommendation_id")
    private int userRecommendationId;

    @Column(name = "track_id")
    private int trackId;

    public int getUserRecommendationId() {
        return userRecommendationId;
    }

    public void setUserRecommendationId(int userRecommendationId) {
        this.userRecommendationId = userRecommendationId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecommendationTrackId that = (UserRecommendationTrackId) o;
        return userRecommendationId == that.userRecommendationId && trackId == that.trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRecommendationId, trackId);
    }

    @Override
    public String toString() {
        return "UserRecommendationTrackId{" +
                "userRecommendationId=" + userRecommendationId +
                ", trackId=" + trackId +
                '}';
    }
}
