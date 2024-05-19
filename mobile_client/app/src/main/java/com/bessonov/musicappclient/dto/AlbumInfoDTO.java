package com.bessonov.musicappclient.dto;

import java.util.List;

public class AlbumInfoDTO {
    private AlbumDTO album;
    private List<ArtistDTO> artist;
    private List<ArtistDTO> featuredArtist;
    private List<Integer> trackId;

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    public List<ArtistDTO> getArtist() {
        return artist;
    }

    public void setArtist(List<ArtistDTO> artist) {
        this.artist = artist;
    }

    public List<ArtistDTO> getFeaturedArtist() {
        return featuredArtist;
    }

    public void setFeaturedArtist(List<ArtistDTO> featuredArtist) {
        this.featuredArtist = featuredArtist;
    }

    public List<Integer> getTrackId() {
        return trackId;
    }

    public void setTrackId(List<Integer> trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "AlbumInfoDTO{" +
                "album=" + album +
                ", artist=" + artist +
                ", featuredArtist=" + featuredArtist +
                ", trackId=" + trackId +
                '}';
    }
}
