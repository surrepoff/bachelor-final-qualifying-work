package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

import java.util.Date;

public class PlaylistDTO {
    private int id;
    private String name;
    private Date creationDate;
    private Date lastUpdateDate;

    public PlaylistDTO() {
        this.id = -1;
        this.name = "";
        this.creationDate = new Date(0);
        this.lastUpdateDate = new Date(0);
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

    @NonNull
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
