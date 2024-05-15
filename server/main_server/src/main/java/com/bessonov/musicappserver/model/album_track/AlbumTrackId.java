package com.bessonov.musicappserver.model.album_track;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlbumTrackId implements Serializable {
    @Column(name = "album_id")
    private int album_id;

    @Column(name = "track_id")
    private int track_id;

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    @Override
    public String toString() {
        return "AlbumTrackId{" +
                "album_id=" + album_id +
                ", track_id=" + track_id +
                '}';
    }
}
