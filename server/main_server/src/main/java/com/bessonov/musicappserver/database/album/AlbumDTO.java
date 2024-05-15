package com.bessonov.musicappserver.database.album;

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

    public AlbumDTO (Album album) {
        if (album == null) {
            this.id = -1;
            this.name = "";
            this.releaseDate = new Date(0);
        }
        else {
            this.id = album.getId();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
        }
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

    @Override
    public String toString() {
        return "AlbumDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
