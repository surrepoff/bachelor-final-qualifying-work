package com.bessonov.musicappserver.database.track;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "primary_genre_id")
    private int primaryGenreId;

    @Column(name = "license_id")
    private int licenseId;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "duration_in_seconds")
    private int durationInSeconds;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "audio_filename", columnDefinition = "TEXT")
    private String audioFilename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimaryGenreId() {
        return primaryGenreId;
    }

    public void setPrimaryGenreId(int primaryGenreId) {
        this.primaryGenreId = primaryGenreId;
    }

    public int getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId = licenseId;
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

    public String getAudioFilename() {
        return audioFilename;
    }

    public void setAudioFilename(String audioFilename) {
        this.audioFilename = audioFilename;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", primaryGenreId=" + primaryGenreId +
                ", licenseId=" + licenseId +
                ", name='" + name + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                ", releaseDate=" + releaseDate +
                ", audioFilename='" + audioFilename + '\'' +
                '}';
    }
}
