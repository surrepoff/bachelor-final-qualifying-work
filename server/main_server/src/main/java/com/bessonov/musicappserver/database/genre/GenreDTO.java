package com.bessonov.musicappserver.database.genre;

public class GenreDTO {
    private int id;
    private String name;

    public GenreDTO() {
        this.id = -1;
        this.name = "";
    }

    public GenreDTO(Genre genre) {
        if (genre == null) {
            this.id = -1;
            this.name = "";
        } else {
            this.id = genre.getId();
            this.name = genre.getName();
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
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
