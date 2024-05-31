package com.bessonov.musicappserver.database.trackSegmentAudioFeature;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "track_segment_audio_feature")
public class TrackSegmentAudioFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "track_audio_feature_id")
    private int trackAudioFeatureId;

    @Column(name = "segment_number")
    private int segment_number;

    @Column(name = "data")
    private double[] data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrackAudioFeatureId() {
        return trackAudioFeatureId;
    }

    public void setTrackAudioFeatureId(int trackAudioFeatureId) {
        this.trackAudioFeatureId = trackAudioFeatureId;
    }

    public int getSegment_number() {
        return segment_number;
    }

    public void setSegment_number(int segment_number) {
        this.segment_number = segment_number;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TrackSegmentAudioFeature{" +
                "id=" + id +
                ", trackAudioFeatureId=" + trackAudioFeatureId +
                ", segment_number=" + segment_number +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
