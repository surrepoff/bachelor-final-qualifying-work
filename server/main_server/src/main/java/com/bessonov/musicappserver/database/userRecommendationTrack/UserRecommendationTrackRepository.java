package com.bessonov.musicappserver.database.userRecommendationTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecommendationTrackRepository extends JpaRepository<UserRecommendationTrack, UserRecommendationTrackId> {
    public List<UserRecommendationTrack> findByIdUserRecommendationIdOrderByTrackNumberInRecommendationAsc(int userRecommendationId);
    public void deleteByIdUserRecommendationId(int userRecommendationId);
}
