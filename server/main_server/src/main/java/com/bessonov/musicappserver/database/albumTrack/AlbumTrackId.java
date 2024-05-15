package com.bessonov.musicappserver.database.albumTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlbumTrackId implements Serializable {
    @Column(name = "album_id")
    private int albumId;

    @Column(name = "track_id")
    private int trackId;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "AlbumTrackId{" +
                "albumId=" + albumId +
                ", trackId=" + trackId +
                '}';
    }
}
