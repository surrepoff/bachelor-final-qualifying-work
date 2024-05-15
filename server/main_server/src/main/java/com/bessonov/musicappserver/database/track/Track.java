package com.bessonov.musicappserver.database.track;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "primary_genre_id")
    private int primary_genre_id;

    @Column(name = "license_id")
    private int license_id;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "duration_in_seconds")
    private int duration_in_seconds;

    @Column(name = "release_date")
    private Date release_date;

    @Column(name = "audio_filename", columnDefinition = "TEXT")
    private String audio_filename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimary_genre_id() {
        return primary_genre_id;
    }

    public void setPrimary_genre_id(int primary_genre_id) {
        this.primary_genre_id = primary_genre_id;
    }

    public int getLicense_id() {
        return license_id;
    }

    public void setLicense_id(int license_id) {
        this.license_id = license_id;
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

    public String getAudio_filename() {
        return audio_filename;
    }

    public void setAudio_filename(String audio_filename) {
        this.audio_filename = audio_filename;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", primary_genre_id=" + primary_genre_id +
                ", license_id=" + license_id +
                ", name='" + name + '\'' +
                ", duration_in_seconds=" + duration_in_seconds +
                ", release_date=" + release_date +
                ", audio_filename='" + audio_filename + '\'' +
                '}';
    }
}
