package com.bessonov.musicappserver.database.userNeuralNetworkConfiguration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNeuralNetworkConfigurationRepository extends JpaRepository<UserNeuralNetworkConfiguration, Integer> {

}
