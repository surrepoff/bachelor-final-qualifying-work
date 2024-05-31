package com.bessonov.musicappserver.database.userRecommendationTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecommendationTrackRepository extends JpaRepository<UserRecommendationTrack, UserRecommendationTrackId> {

}
