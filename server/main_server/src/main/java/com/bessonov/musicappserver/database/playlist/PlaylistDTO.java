package com.bessonov.musicappserver.database.playlist;

import java.util.Date;

public class PlaylistDTO {
    private int id;
    private String name;
    private Date creationDate;
    private Date lastUpdateDate;

    public PlaylistDTO () {
        this.id = -1;
        this.name = "";
        this.creationDate = new Date(0);
        this.lastUpdateDate = new Date(0);
    }

    public PlaylistDTO (Playlist playlist) {
        if (playlist == null) {
            this.id = -1;
            this.name = "";
            this.creationDate = new Date(0);
            this.lastUpdateDate = new Date(0);
        }
        else {
            this.id = playlist.getId();
            this.name = playlist.getName();
            this.creationDate = playlist.getCreationDate();
            this.lastUpdateDate = playlist.getLastUpdateDate();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        return "PlaylistDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
