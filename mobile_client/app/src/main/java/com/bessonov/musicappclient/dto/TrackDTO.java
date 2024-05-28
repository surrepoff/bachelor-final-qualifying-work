package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.Date;

public class TrackDTO {
    private int id;
    private String name;
    private int durationInSeconds;
    private Date releaseDate;

    public TrackDTO () {
        this.id = -1;
        this.name = "";
        this.durationInSeconds = -1;
        this.releaseDate = new Date(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "TrackDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
