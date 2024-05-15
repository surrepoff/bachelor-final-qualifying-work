package com.bessonov.musicappserver.database.album_artist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;

@Entity
@Table(name = "album_artist")
public class AlbumArtist {
    @EmbeddedId
    private AlbumArtistId id;

    @Column(name = "artist_status_id")
    private int artist_status_id;

    public AlbumArtistId getId() {
        return id;
    }

    public void setId(AlbumArtistId id) {
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
        return "AlbumArtist{" +
                "id=" + id +
                ", artist_status_id=" + artist_status_id +
                '}';
    }
}
