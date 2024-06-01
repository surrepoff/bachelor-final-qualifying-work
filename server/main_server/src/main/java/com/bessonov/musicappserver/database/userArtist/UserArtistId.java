package com.bessonov.musicappserver.database.userArtist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserArtistId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "artist_id")
    private int artistId;

    public UserArtistId() {
    }

    public UserArtistId(int userId, int artistId) {
        this.userId = userId;
        this.artistId = artistId;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserArtistId that = (UserArtistId) o;
        return userId == that.userId && artistId == that.artistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, artistId);
    }

    @Override
    public String toString() {
        return "UserArtistId{" +
                "userId=" + userId +
                ", artistId=" + artistId +
                '}';
    }
}
