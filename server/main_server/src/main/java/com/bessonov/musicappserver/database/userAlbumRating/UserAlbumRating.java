package com.bessonov.musicappserver.database.userAlbumRating;

import com.bessonov.musicappserver.database.userAlbum.UserAlbumId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_album_rating")
public class UserAlbumRating {
    @EmbeddedId
    private UserAlbumId id;

    @Column(name = "user_rating_id")
    private int userRatingId;

    public UserAlbumId getId() {
        return id;
    }

    public void setId(UserAlbumId id) {
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
        return "UserAlbumRating{" +
                "id=" + id +
                ", userRatingId=" + userRatingId +
                '}';
    }
}
