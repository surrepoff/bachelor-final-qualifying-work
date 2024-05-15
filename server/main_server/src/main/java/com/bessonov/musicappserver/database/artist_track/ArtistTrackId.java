package com.bessonov.musicappserver.database.artist_track;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ArtistTrackId implements Serializable {
    @Column(name = "artist_id")
    private int artist_id;

    @Column(name = "track_id")
    private int track_id;

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    @Override
    public String toString() {
        return "ArtistTrackId{" +
                "artist_id=" + artist_id +
                ", track_id=" + track_id +
                '}';
    }
}
