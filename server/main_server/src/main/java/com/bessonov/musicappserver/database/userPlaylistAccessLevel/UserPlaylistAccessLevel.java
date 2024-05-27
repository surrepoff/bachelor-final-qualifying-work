package com.bessonov.musicappserver.database.userPlaylistAccessLevel;

import jakarta.persistence.*;

@Entity
@Table(name = "user_playlist_access_level")
public class UserPlaylistAccessLevel {
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
        return "UserPlaylistAccessLevel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
