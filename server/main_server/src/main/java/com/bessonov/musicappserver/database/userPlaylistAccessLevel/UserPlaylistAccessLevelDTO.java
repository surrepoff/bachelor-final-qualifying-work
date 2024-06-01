package com.bessonov.musicappserver.database.userPlaylistAccessLevel;

public class UserPlaylistAccessLevelDTO {
    private int id;
    private String name;

    public UserPlaylistAccessLevelDTO() {
        this.id = -1;
        this.name = "None";
    }

    public UserPlaylistAccessLevelDTO(UserPlaylistAccessLevel userPlaylistAccessLevel) {
        if (userPlaylistAccessLevel == null) {
            this.id = -1;
            this.name = "None";
        }
        else {
            this.id = userPlaylistAccessLevel.getId();
            this.name = userPlaylistAccessLevel.getName();
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
        return "UserPlaylistAccessLevelDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
