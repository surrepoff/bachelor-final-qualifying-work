package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.List;

public class PlaylistCreateDTO {
    private String name;
    private List<Integer> trackId;

    public PlaylistCreateDTO(String name, List<Integer> trackId) {
        this.name = name;
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getTrackId() {
        return trackId;
    }

    public void setTrackId(List<Integer> trackId) {
        this.trackId = trackId;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlaylistCreateDTO{" +
                "name='" + name + '\'' +
                ", trackId=" + trackId +
                '}';
    }
}
