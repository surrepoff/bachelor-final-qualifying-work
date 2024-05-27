package com.bessonov.musicappserver.database.userPlaylist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserPlaylistId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "playlist_id")
    private int playlistId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public String toString() {
        return "UserPlaylistId{" +
                "userId=" + userId +
                ", playlistId=" + playlistId +
                '}';
    }
}
