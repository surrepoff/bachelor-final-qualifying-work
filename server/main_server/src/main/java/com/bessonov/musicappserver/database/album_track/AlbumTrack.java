package com.bessonov.musicappserver.database.album_track;

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
    private int track_number_in_album;

    public AlbumTrackId getId() {
        return id;
    }

    public void setId(AlbumTrackId id) {
        this.id = id;
    }

    public int getTrack_number_in_album() {
        return track_number_in_album;
    }

    public void setTrack_number_in_album(int track_number_in_album) {
        this.track_number_in_album = track_number_in_album;
    }

    @Override
    public String toString() {
        return "AlbumTrack{" +
                "id=" + id +
                ", track_number_in_album=" + track_number_in_album +
                '}';
    }
}
