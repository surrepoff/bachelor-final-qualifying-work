package com.bessonov.musicappserver.database.albumArtist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlbumArtistId implements Serializable {
    @Column(name = "album_id")
    private int albumId;

    @Column(name = "artist_id")
    private int artistId;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "AlbumArtistId{" +
                "albumId=" + albumId +
                ", artistId=" + artistId +
                '}';
    }
}
