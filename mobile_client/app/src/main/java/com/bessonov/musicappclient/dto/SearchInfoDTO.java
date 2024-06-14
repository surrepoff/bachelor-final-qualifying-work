package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchInfoDTO {
    private List<AlbumInfoDTO> foundedAlbum;
    private List<ArtistInfoDTO> foundedArtist;
    private List<PlaylistInfoDTO> foundedPlaylist;
    private List<TrackInfoDTO> foundedTrack;

    public List<AlbumInfoDTO> getFoundedAlbum() {
        return foundedAlbum;
    }

    public void setFoundedAlbum(List<AlbumInfoDTO> foundedAlbum) {
        this.foundedAlbum = foundedAlbum;
    }

    public List<ArtistInfoDTO> getFoundedArtist() {
        return foundedArtist;
    }

    public void setFoundedArtist(List<ArtistInfoDTO> foundedArtist) {
        this.foundedArtist = foundedArtist;
    }

    public List<PlaylistInfoDTO> getFoundedPlaylist() {
        return foundedPlaylist;
    }

    public void setFoundedPlaylist(List<PlaylistInfoDTO> foundedPlaylist) {
        this.foundedPlaylist = foundedPlaylist;
    }

    public List<TrackInfoDTO> getFoundedTrack() {
        return foundedTrack;
    }

    public void setFoundedTrack(List<TrackInfoDTO> foundedTrack) {
        this.foundedTrack = foundedTrack;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchInfoDTO{" +
                "foundedAlbum=" + foundedAlbum +
                ", foundedArtist=" + foundedArtist +
                ", foundedPlaylist=" + foundedPlaylist +
                ", foundedTrack=" + foundedTrack +
                '}';
    }
}
