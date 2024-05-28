package com.bessonov.musicappserver.database.userPlaylistRating;

import com.bessonov.musicappserver.database.userPlaylist.UserPlaylistId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_playlist_rating")
public class UserPlaylistRating {
    @EmbeddedId
    private UserPlaylistId id;

    @Column(name = "user_rating_id")
    private int userRatingId;

    public UserPlaylistId getId() {
        return id;
    }

    public void setId(UserPlaylistId id) {
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
        return "UserPlaylistRating{" +
                "id=" + id +
                ", userRatingId=" + userRatingId +
                '}';
    }
}
