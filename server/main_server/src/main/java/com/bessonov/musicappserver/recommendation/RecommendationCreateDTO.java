package com.bessonov.musicappserver.recommendation;

import java.util.List;

public class RecommendationCreateDTO {
    private int userId;
    private int extractionTypeId;
    private int size;
    private List<Integer> genreId;
    private double familiarityPercentage;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExtractionTypeId() {
        return extractionTypeId;
    }

    public void setExtractionTypeId(int extractionTypeId) {
        this.extractionTypeId = extractionTypeId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Integer> getGenreId() {
        return genreId;
    }

    public void setGenreId(List<Integer> genreId) {
        this.genreId = genreId;
    }

    public double getFamiliarityPercentage() {
        return familiarityPercentage;
    }

    public void setFamiliarityPercentage(double familiarityPercentage) {
        this.familiarityPercentage = familiarityPercentage;
    }

    @Override
    public String toString() {
        return "RecommendationCreateDTO{" +
                "userId=" + userId +
                ", extractionTypeId=" + extractionTypeId +
                ", size=" + size +
                ", genreId=" + genreId +
                ", familiarityPercentage=" + familiarityPercentage +
                '}';
    }
}
