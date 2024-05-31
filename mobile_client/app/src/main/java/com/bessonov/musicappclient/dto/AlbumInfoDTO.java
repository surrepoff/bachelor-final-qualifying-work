package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class AlbumInfoDTO {
    private AlbumDTO album;
    private List<ArtistDTO> artist;
    private List<ArtistDTO> featuredArtist;
    private List<Integer> trackId;
    private UserRatingDTO rating;
    private UserAlbumDTO isAdded;

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

    public UserRatingDTO getRating() {
        return rating;
    }

    public void setRating(UserRatingDTO rating) {
        this.rating = rating;
    }

    public UserAlbumDTO getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(UserAlbumDTO isAdded) {
        this.isAdded = isAdded;
    }

    @NonNull
    @Override
    public String toString() {
        return "AlbumInfoDTO{" +
                "album=" + album +
                ", artist=" + artist +
                ", featuredArtist=" + featuredArtist +
                ", trackId=" + trackId +
                ", rating=" + rating +
                ", isAdded=" + isAdded +
                '}';
    }
}
