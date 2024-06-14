package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchInfoDTO {
    private List<AlbumInfoDTO> album;
    private List<ArtistInfoDTO> artist;
    private List<PlaylistInfoDTO> playlist;
    private List<TrackInfoDTO> track;

    public List<AlbumInfoDTO> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumInfoDTO> album) {
        this.album = album;
    }

    public List<ArtistInfoDTO> getArtist() {
        return artist;
    }

    public void setArtist(List<ArtistInfoDTO> artist) {
        this.artist = artist;
    }

    public List<PlaylistInfoDTO> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<PlaylistInfoDTO> playlist) {
        this.playlist = playlist;
    }

    public List<TrackInfoDTO> getTrack() {
        return track;
    }

    public void setTrack(List<TrackInfoDTO> track) {
        this.track = track;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchInfoDTO{" +
                "album=" + album +
                ", artist=" + artist +
                ", playlist=" + playlist +
                ", track=" + track +
                '}';
    }
}
