package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class PlaylistInfoDTO {
    private PlaylistDTO playlist;
    private List<UserDataShortDTO> owner;
    private List<Integer> trackId;
    private UserRatingDTO rating;
    private UserPlaylistAccessLevelDTO accessLevel;
    private UserPlaylistDTO isAdded;

    public PlaylistDTO getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistDTO playlist) {
        this.playlist = playlist;
    }

    public List<UserDataShortDTO> getOwner() {
        return owner;
    }

    public void setOwner(List<UserDataShortDTO> owner) {
        this.owner = owner;
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

    public UserPlaylistAccessLevelDTO getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(UserPlaylistAccessLevelDTO accessLevel) {
        this.accessLevel = accessLevel;
    }

    public UserPlaylistDTO getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(UserPlaylistDTO isAdded) {
        this.isAdded = isAdded;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlaylistInfoDTO{" +
                "playlist=" + playlist +
                ", owner=" + owner +
                ", trackId=" + trackId +
                ", rating=" + rating +
                ", accessLevel=" + accessLevel +
                ", isAdded=" + isAdded +
                '}';
    }
}
