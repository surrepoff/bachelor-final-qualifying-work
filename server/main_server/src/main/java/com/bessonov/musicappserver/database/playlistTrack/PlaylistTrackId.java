package com.bessonov.musicappserver.database.playlistTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PlaylistTrackId implements Serializable {
    @Column(name = "playlist_id")
    private int playlistId;

    @Column(name = "track_id")
    private int trackId;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "PlaylistTrackId{" +
                "playlistId=" + playlistId +
                ", trackId=" + trackId +
                '}';
    }
}
