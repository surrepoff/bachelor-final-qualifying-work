package com.bessonov.musicappserver.database.userRecommendationGenre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecommendationGenreRepository extends JpaRepository<UserRecommendationGenre, UserRecommendationGenreId> {

}
