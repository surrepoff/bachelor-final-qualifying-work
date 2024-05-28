package com.bessonov.musicappserver.database.userPlaylist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPlaylistId that = (UserPlaylistId) o;
        return userId == that.userId && playlistId == that.playlistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, playlistId);
    }

    @Override
    public String toString() {
        return "UserPlaylistId{" +
                "userId=" + userId +
                ", playlistId=" + playlistId +
                '}';
    }
}
