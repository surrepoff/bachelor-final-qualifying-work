package com.bessonov.musicappserver.database.albumArtist;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "album_artist")
public class AlbumArtist {
    @EmbeddedId
    private AlbumArtistId id;

    @Column(name = "artist_status_id")
    private int artistStatusId;

    public AlbumArtistId getId() {
        return id;
    }

    public void setId(AlbumArtistId id) {
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
        return "AlbumArtist{" +
                "id=" + id +
                ", artistStatusId=" + artistStatusId +
                '}';
    }
}
