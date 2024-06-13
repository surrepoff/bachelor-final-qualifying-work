package com.bessonov.musicappclient.dto;

import androidx.annotation.NonNull;

public class RecommendationResponseDTO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "RecommendationResponseDTO{" +
                "id=" + id +
                '}';
    }
}
