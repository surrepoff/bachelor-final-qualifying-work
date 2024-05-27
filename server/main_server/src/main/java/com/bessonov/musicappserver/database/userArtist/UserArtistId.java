package com.bessonov.musicappserver.database.userArtist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserArtistId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "artist_id")
    private int artistId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "UserArtistId{" +
                "userId=" + userId +
                ", artistId=" + artistId +
                '}';
    }
}
