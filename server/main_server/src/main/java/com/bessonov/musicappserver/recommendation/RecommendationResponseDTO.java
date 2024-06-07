package com.bessonov.musicappserver.recommendation;

public class RecommendationResponseDTO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RecommendationResponseDTO{" +
                "id=" + id +
                '}';
    }
}
