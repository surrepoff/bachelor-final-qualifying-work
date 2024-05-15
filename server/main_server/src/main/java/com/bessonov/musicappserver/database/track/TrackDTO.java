package com.bessonov.musicappserver.database.track;

import java.sql.Date;

public class TrackDTO {
    private int id;
    private String name;
    private int duration_in_seconds;
    private Date release_date;

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

    public int getDuration_in_seconds() {
        return duration_in_seconds;
    }

    public void setDuration_in_seconds(int duration_in_seconds) {
        this.duration_in_seconds = duration_in_seconds;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "TrackDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration_in_seconds=" + duration_in_seconds +
                ", release_date=" + release_date +
                '}';
    }
}
