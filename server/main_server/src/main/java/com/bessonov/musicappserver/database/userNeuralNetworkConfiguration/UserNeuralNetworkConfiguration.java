package com.bessonov.musicappserver.database.userNeuralNetworkConfiguration;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "user_neural_network_configuration")
public class UserNeuralNetworkConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "track_audio_feature_extraction_type_id")
    private int trackAudioFeatureExtractionTypeId;

    @Column(name = "training_date")
    private Date trainingDate;

    @Column(name = "data")
    private float data;

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

    public int getTrackAudioFeatureExtractionTypeId() {
        return trackAudioFeatureExtractionTypeId;
    }

    public void setTrackAudioFeatureExtractionTypeId(int trackAudioFeatureExtractionTypeId) {
        this.trackAudioFeatureExtractionTypeId = trackAudioFeatureExtractionTypeId;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserNeuralNetworkConfiguration{" +
                "id=" + id +
                ", userId=" + userId +
                ", trackAudioFeatureExtractionTypeId=" + trackAudioFeatureExtractionTypeId +
                ", trainingDate=" + trainingDate +
                ", data=" + data +
                '}';
    }
}
