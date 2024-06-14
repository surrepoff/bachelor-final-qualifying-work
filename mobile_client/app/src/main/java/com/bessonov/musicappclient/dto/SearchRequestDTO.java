package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

public class SearchRequestDTO {
    private String name;

    public SearchRequestDTO() {
    }

    public SearchRequestDTO(String name) {
        this.name = name;
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
        return "SearchRequestDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
