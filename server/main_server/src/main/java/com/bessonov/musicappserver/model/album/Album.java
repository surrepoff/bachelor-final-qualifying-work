package com.bessonov.musicappserver.model.album;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "release_date")
    private Date release_date;

    @Column(name = "image_filename", columnDefinition = "TEXT")
    private String image_filename;

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

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getImage_filename() {
        return image_filename;
    }

    public void setImage_filename(String image_filename) {
        this.image_filename = image_filename;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", release_date=" + release_date +
                ", image_filename='" + image_filename + '\'' +
                '}';
    }
}
