package com.bessonov.musicappserver.database.userTrackRating;

import com.bessonov.musicappserver.database.userTrack.UserTrackId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_track_rating")
public class UserTrackRating {
    @EmbeddedId
    private UserTrackId id;

    @Column(name = "user_rating_id")
    private int userRatingId;

    public UserTrackId getId() {
        return id;
    }

    public void setId(UserTrackId id) {
        this.id = id;
    }

    public int getUserRatingId() {
        return userRatingId;
    }

    public void setUserRatingId(int userRatingId) {
        this.userRatingId = userRatingId;
    }

    @Override
    public String toString() {
        return "UserTrackRating{" +
                "id=" + id +
                ", userRatingId=" + userRatingId +
                '}';
    }
}
