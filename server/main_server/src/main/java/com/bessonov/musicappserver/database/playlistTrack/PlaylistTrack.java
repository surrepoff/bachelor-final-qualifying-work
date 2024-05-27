package com.bessonov.musicappserver.database.playlistTrack;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist_track")
public class PlaylistTrack {
    @EmbeddedId
    private PlaylistTrackId id;

    @Column(name = "track_number_in_playlist")
    private int trackNumberInPlaylist;

    public PlaylistTrackId getId() {
        return id;
    }

    public void setId(PlaylistTrackId id) {
        this.id = id;
    }

    public int getTrackNumberInPlaylist() {
        return trackNumberInPlaylist;
    }

    public void setTrackNumberInPlaylist(int trackNumberInPlaylist) {
        this.trackNumberInPlaylist = trackNumberInPlaylist;
    }

    @Override
    public String toString() {
        return "PlaylistTrack{" +
                "id=" + id +
                ", trackNumberInPlaylist=" + trackNumberInPlaylist +
                '}';
    }
}
