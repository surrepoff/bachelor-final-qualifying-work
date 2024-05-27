package com.bessonov.musicappserver.database.trackAudioFeatureExtractionType;

import jakarta.persistence.*;

@Entity
@Table(name = "track_audio_feature_extraction_type")
public class TrackAudioFeatureExtractionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_delta")
    private int startDelta;

    @Column(name = "segment_duration")
    private int segmentDuration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartDelta() {
        return startDelta;
    }

    public void setStartDelta(int startDelta) {
        this.startDelta = startDelta;
    }

    public int getSegmentDuration() {
        return segmentDuration;
    }

    public void setSegmentDuration(int segmentDuration) {
        this.segmentDuration = segmentDuration;
    }

    @Override
    public String toString() {
        return "TrackAudioFeatureExtractionType{" +
                "id=" + id +
                ", startDelta=" + startDelta +
                ", segmentDuration=" + segmentDuration +
                '}';
    }
}
