package com.bessonov.musicappserver.database.trackAudioFeature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackAudioFeatureRepository extends JpaRepository<TrackAudioFeature, Integer> {

}
