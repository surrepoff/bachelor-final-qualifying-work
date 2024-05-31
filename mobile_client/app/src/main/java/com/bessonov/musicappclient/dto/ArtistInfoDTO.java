package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class ArtistInfoDTO {
    private ArtistDTO artist;
    private List<Integer> albumId;
    private List<Integer> trackId;
    private UserRatingDTO rating;
    private UserArtistDTO isAdded;

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

    public UserRatingDTO getRating() {
        return rating;
    }

    public void setRating(UserRatingDTO rating) {
        this.rating = rating;
    }

    public UserArtistDTO getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(UserArtistDTO isAdded) {
        this.isAdded = isAdded;
    }

    @NonNull
    @Override
    public String toString() {
        return "ArtistInfoDTO{" +
                "artist=" + artist +
                ", albumId=" + albumId +
                ", trackId=" + trackId +
                ", rating=" + rating +
                ", isAdded=" + isAdded +
                '}';
    }
}
