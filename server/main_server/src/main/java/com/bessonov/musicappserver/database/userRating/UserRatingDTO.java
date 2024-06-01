package com.bessonov.musicappserver.database.userRating;

public class UserRatingDTO {
    private int id;
    private String name;

    public UserRatingDTO() {
        this.id = 0;
        this.name = "none";
    }

    public UserRatingDTO(UserRating userRating) {
        if (userRating == null) {
            this.id = 0;
            this.name = "none";
        }
        else {
            this.id = userRating.getId();
            this.name = userRating.getName();
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
        return "UserRatingDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
