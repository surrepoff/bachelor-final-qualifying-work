package com.bessonov.musicappserver.database.userNeuralNetworkConfiguration;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;

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

    @Column(name = "model_config", columnDefinition = "json")
    private String modelConfig;

    @Column(name = "model_weights", columnDefinition = "bytea")
    private byte[] modelWeights;

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

    public String getModelConfig() {
        return modelConfig;
    }

    public void setModelConfig(String modelConfig) {
        this.modelConfig = modelConfig;
    }

    public byte[] getModelWeights() {
        return modelWeights;
    }

    public void setModelWeights(byte[] modelWeights) {
        this.modelWeights = modelWeights;
    }

    @Override
    public String toString() {
        return "UserNeuralNetworkConfiguration{" +
                "id=" + id +
                ", userId=" + userId +
                ", trackAudioFeatureExtractionTypeId=" + trackAudioFeatureExtractionTypeId +
                ", trainingDate=" + trainingDate +
                ", modelConfig='" + modelConfig + '\'' +
                ", modelWeights=" + Arrays.toString(modelWeights) +
                '}';
    }
}
