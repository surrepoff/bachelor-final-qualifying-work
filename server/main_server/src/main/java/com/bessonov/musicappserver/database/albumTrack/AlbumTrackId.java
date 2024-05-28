package com.bessonov.musicappserver.database.albumTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumTrackId that = (AlbumTrackId) o;
        return albumId == that.albumId && trackId == that.trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, trackId);
    }

    @Override
    public String toString() {
        return "AlbumTrackId{" +
                "albumId=" + albumId +
                ", trackId=" + trackId +
                '}';
    }
}
