package com.bessonov.musicappserver.database.userArtistRating;

import com.bessonov.musicappserver.database.userArtist.UserArtistId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_artist_rating")
public class UserArtistRating {
    @EmbeddedId
    private UserArtistId id;

    @Column(name = "user_rating_id")
    private int userRatingId;

    public UserArtistId getId() {
        return id;
    }

    public void setId(UserArtistId id) {
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
        return "UserArtistRating{" +
                "id=" + id +
                ", userRatingId=" + userRatingId +
                '}';
    }
}
