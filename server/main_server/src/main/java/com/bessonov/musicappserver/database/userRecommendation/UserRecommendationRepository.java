package com.bessonov.musicappserver.database.userRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Integer> {

}
