package com.bessonov.musicappserver.database.userRecommendation;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_recommendation")
public class UserRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_neural_network_configuration_id")
    private int userNeuralNetworkConfigurationId;

    @Column(name = "user_rating_id")
    private int userRatingId;

    @Column(name = "familiarity_percentage")
    private double familiarityPercentage;

    @Column(name = "creation_date")
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserNeuralNetworkConfigurationId() {
        return userNeuralNetworkConfigurationId;
    }

    public void setUserNeuralNetworkConfigurationId(int userNeuralNetworkConfigurationId) {
        this.userNeuralNetworkConfigurationId = userNeuralNetworkConfigurationId;
    }

    public int getUserRatingId() {
        return userRatingId;
    }

    public void setUserRatingId(int userRatingId) {
        this.userRatingId = userRatingId;
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
        return "UserRecommendation{" +
                "id=" + id +
                ", userId=" + userId +
                ", userNeuralNetworkConfigurationId=" + userNeuralNetworkConfigurationId +
                ", userRatingId=" + userRatingId +
                ", familiarityPercentage=" + familiarityPercentage +
                ", creationDate=" + creationDate +
                '}';
    }
}
