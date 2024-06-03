package com.bessonov.musicappserver.database.userRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Integer> {
    public List<UserRecommendation> findByUserIdOrderByCreationDateAsc(int userId);
}
