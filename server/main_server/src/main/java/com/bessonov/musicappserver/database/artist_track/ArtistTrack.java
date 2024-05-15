package com.bessonov.musicappserver.database.artist_track;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist_track")
public class ArtistTrack {
    @EmbeddedId
    private ArtistTrackId id;

    @Column(name = "artist_status_id")
    private int artist_status_id;

    public ArtistTrackId getId() {
        return id;
    }

    public void setId(ArtistTrackId id) {
        this.id = id;
    }

    public int getArtist_status_id() {
        return artist_status_id;
    }

    public void setArtist_status_id(int artist_status_id) {
        this.artist_status_id = artist_status_id;
    }

    @Override
    public String toString() {
        return "ArtistTrack{" +
                "id=" + id +
                ", artist_status_id=" + artist_status_id +
                '}';
    }
}
