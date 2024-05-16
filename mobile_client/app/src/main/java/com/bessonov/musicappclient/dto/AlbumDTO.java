package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.sql.Date;

public class AlbumDTO {
    private int id;
    private String name;
    private Date releaseDate;

    public AlbumDTO () {
        this.id = -1;
        this.name = "";
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "AlbumDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
