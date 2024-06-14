package com.bessonov.musicappserver.database.userRecommendationGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecommendationGenreRepository extends JpaRepository<UserRecommendationGenre, UserRecommendationGenreId> {
    List<UserRecommendationGenre> findByIdUserRecommendationIdOrderByIdGenreIdAsc(int userRecommendationId);

    void deleteByIdUserRecommendationId(int userRecommendationId);
}
