package com.bessonov.musicappserver.model.artist_status;

import jakarta.persistence.*;

@Entity
@Table(name = "artist_status")
public class ArtistStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

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

    @Override
    public String toString() {
        return "ArtistStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
