package com.bessonov.musicappserver.database.trackAudioFeatureExtractionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackAudioFeatureExtractionTypeRepository extends JpaRepository<TrackAudioFeatureExtractionType, Integer> {

}
