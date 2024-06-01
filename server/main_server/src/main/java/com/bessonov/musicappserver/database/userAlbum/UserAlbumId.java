package com.bessonov.musicappserver.database.userAlbum;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserAlbumId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "album_id")
    private int albumId;

    public UserAlbumId() {
    }

    public UserAlbumId(int userId, int albumId) {
        this.userId = userId;
        this.albumId = albumId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAlbumId that = (UserAlbumId) o;
        return userId == that.userId && albumId == that.albumId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, albumId);
    }

    @Override
    public String toString() {
        return "UserAlbumId{" +
                "userId=" + userId +
                ", albumId=" + albumId +
                '}';
    }
}
