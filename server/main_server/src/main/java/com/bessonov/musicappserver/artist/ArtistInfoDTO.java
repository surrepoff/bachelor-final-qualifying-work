package com.bessonov.musicappserver.artist;

import com.bessonov.musicappserver.database.artist.ArtistDTO;

import java.util.List;

public class ArtistInfoDTO {
    private ArtistDTO artist;
    private List<Integer> albumId;
    private List<Integer> trackId;

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    public List<Integer> getAlbumId() {
        return albumId;
    }

    public void setAlbumId(List<Integer> albumId) {
        this.albumId = albumId;
    }

    public List<Integer> getTrackId() {
        return trackId;
    }

    public void setTrackId(List<Integer> trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "ArtistInfoDTO{" +
                "artist=" + artist +
                ", albumId=" + albumId +
                ", trackId=" + trackId +
                '}';
    }
}
