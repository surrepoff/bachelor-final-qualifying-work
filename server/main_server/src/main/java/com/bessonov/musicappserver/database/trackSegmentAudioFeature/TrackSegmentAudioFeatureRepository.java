package com.bessonov.musicappserver.database.trackSegmentAudioFeature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackSegmentAudioFeatureRepository extends JpaRepository<TrackSegmentAudioFeature, Integer> {

}
