package com.bessonov.musicappserver.database.artistTrack;

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
    private int artistStatusId;

    public ArtistTrackId getId() {
        return id;
    }

    public void setId(ArtistTrackId id) {
        this.id = id;
    }

    public int getArtistStatusId() {
        return artistStatusId;
    }

    public void setArtistStatusId(int artistStatusId) {
        this.artistStatusId = artistStatusId;
    }

    @Override
    public String toString() {
        return "ArtistTrack{" +
                "id=" + id +
                ", artistStatusId=" + artistStatusId +
                '}';
    }
}
