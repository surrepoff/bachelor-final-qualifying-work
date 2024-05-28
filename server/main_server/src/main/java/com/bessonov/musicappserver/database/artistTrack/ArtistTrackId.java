package com.bessonov.musicappserver.database.artistTrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ArtistTrackId implements Serializable {
    @Column(name = "artist_id")
    private int artistId;

    @Column(name = "track_id")
    private int trackId;

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
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
        ArtistTrackId that = (ArtistTrackId) o;
        return artistId == that.artistId && trackId == that.trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId, trackId);
    }

    @Override
    public String toString() {
        return "ArtistTrackId{" +
                "artistId=" + artistId +
                ", trackId=" + trackId +
                '}';
    }
}
