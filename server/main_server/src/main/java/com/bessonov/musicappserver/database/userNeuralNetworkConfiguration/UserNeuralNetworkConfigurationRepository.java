package com.bessonov.musicappserver.database.userNeuralNetworkConfiguration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNeuralNetworkConfigurationRepository extends JpaRepository<UserNeuralNetworkConfiguration, Integer> {
    Optional<UserNeuralNetworkConfiguration> findByUserIdAndTrackAudioFeatureExtractionTypeId(int userId, int trackAudioFeatureExtractionTypeId);
}
