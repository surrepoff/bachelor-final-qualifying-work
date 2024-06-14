package com.bessonov.musicappserver.database.userRecommendation;

import java.util.Date;

public class UserRecommendationDTO {
    private int id;
    private double familiarityPercentage;
    private Date creationDate;

    public UserRecommendationDTO() {
        this.id = -1;
        this.familiarityPercentage = -1;
        this.creationDate = new Date(0);
    }

    public UserRecommendationDTO(UserRecommendation userRecommendation) {
        if (userRecommendation == null) {
            this.id = -1;
            this.familiarityPercentage = -1;
            this.creationDate = new Date(0);
        } else {
            this.id = userRecommendation.getId();
            this.familiarityPercentage = userRecommendation.getFamiliarityPercentage();
            this.creationDate = userRecommendation.getCreationDate();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFamiliarityPercentage() {
        return familiarityPercentage;
    }

    public void setFamiliarityPercentage(double familiarityPercentage) {
        this.familiarityPercentage = familiarityPercentage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserRecommendationDTO{" +
                "id=" + id +
                ", familiarityPercentage=" + familiarityPercentage +
                ", creationDate=" + creationDate +
                '}';
    }
}
