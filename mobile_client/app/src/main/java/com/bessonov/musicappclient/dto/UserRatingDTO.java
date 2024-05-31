package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

public class UserRatingDTO {
    private int id;
    private String name;

    UserRatingDTO() {
        this.id = 0;
        this.name = "none";
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

    @NonNull
    @Override
    public String toString() {
        return "UserRatingDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
