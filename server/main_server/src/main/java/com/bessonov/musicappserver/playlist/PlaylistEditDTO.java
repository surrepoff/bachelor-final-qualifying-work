package com.bessonov.musicappserver.playlist;

import java.util.List;

public class PlaylistEditDTO {
    private String name;
    private List<Integer> trackId;

    public PlaylistEditDTO(String name, List<Integer> trackId) {
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

    @Override
    public String toString() {
        return "PlaylistEditDTO{" +
                "name='" + name + '\'' +
                ", trackId=" + trackId +
                '}';
    }
}
