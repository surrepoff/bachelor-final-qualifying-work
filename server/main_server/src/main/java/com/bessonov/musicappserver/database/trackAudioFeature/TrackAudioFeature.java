package com.bessonov.musicappserver.database.trackAudioFeature;

import jakarta.persistence.*;

@Entity
@Table(name = "track_audio_feature")
public class TrackAudioFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "track_id")
    private int trackId;

    @Column(name = "track_audio_feature_extraction_type_id")
    private int trackAudioFeatureExtractionTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getTrackAudioFeatureExtractionTypeId() {
        return trackAudioFeatureExtractionTypeId;
    }

    public void setTrackAudioFeatureExtractionTypeId(int trackAudioFeatureExtractionTypeId) {
        this.trackAudioFeatureExtractionTypeId = trackAudioFeatureExtractionTypeId;
    }

    @Override
    public String toString() {
        return "TrackAudioFeature{" +
                "id=" + id +
                ", trackId=" + trackId +
                ", trackAudioFeatureExtractionTypeId=" + trackAudioFeatureExtractionTypeId +
                '}';
    }
}
