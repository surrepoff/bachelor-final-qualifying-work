package com.bessonov.musicappserver.database.artist;

public class ArtistDTO {
    private int id;
    private String name;

    public ArtistDTO() {
        this.id = -1;
        this.name = "";
    }

    public ArtistDTO(Artist artist) {
        if (artist == null) {
            this.id = -1;
            this.name = "";
        } else {
            this.id = artist.getId();
            this.name = artist.getName();
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

    @Override
    public String toString() {
        return "ArtistDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
