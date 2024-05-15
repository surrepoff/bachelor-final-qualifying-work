package com.bessonov.musicappserver.database.albumTrack;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "album_track")
public class AlbumTrack {
    @EmbeddedId
    private AlbumTrackId id;

    @Column(name = "track_number_in_album")
    private int trackNumberInAlbum;

    public AlbumTrackId getId() {
        return id;
    }

    public void setId(AlbumTrackId id) {
        this.id = id;
    }

    public int getTrackNumberInAlbum() {
        return trackNumberInAlbum;
    }

    public void setTrackNumberInAlbum(int trackNumberInAlbum) {
        this.trackNumberInAlbum = trackNumberInAlbum;
    }

    @Override
    public String toString() {
        return "AlbumTrack{" +
                "id=" + id +
                ", trackNumberInAlbum=" + trackNumberInAlbum +
                '}';
    }
}
