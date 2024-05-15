package com.bessonov.musicappserver.database.album_artist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlbumArtistId implements Serializable {
    @Column(name = "album_id")
    private int album_id;

    @Column(name = "artist_id")
    private int artist_id;

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    @Override
    public String toString() {
        return "AlbumArtistId{" +
                "album_id=" + album_id +
                ", artist_id=" + artist_id +
                '}';
    }
}
