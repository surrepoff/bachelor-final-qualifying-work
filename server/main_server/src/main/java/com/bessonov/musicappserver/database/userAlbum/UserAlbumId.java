package com.bessonov.musicappserver.database.userAlbum;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserAlbumId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "album_id")
    private int albumId;

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
    public String toString() {
        return "UserAlbumId{" +
                "userId=" + userId +
                ", albumId=" + albumId +
                '}';
    }
}
